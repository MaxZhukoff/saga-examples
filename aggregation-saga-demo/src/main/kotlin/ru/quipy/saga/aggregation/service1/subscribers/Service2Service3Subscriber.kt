package ru.quipy.saga.aggregation.service1.subscribers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import ru.quipy.core.EventSourcingService
import ru.quipy.domain.Aggregate
import ru.quipy.domain.Event
import ru.quipy.saga.SagaContext
import ru.quipy.saga.SagaManager
import ru.quipy.saga.aggregation.service2.api.Service2Aggregate
import ru.quipy.saga.aggregation.service2.api.Service2ProcessedEvent
import ru.quipy.saga.aggregation.service3.api.Service3Aggregate
import ru.quipy.saga.aggregation.service3.api.Service3ProcessedEvent
import ru.quipy.saga.plus
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class Service2Service3Subscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val aggregationRepository: AggregationRepository,
    private val service1EsService: EventSourcingService<UUID, ru.quipy.saga.aggregation.service1.api.Service1Aggregate, ru.quipy.saga.aggregation.service1.logic.Service1>,
    private val sagaManager: SagaManager
) {
    private val logger: Logger = LoggerFactory.getLogger(Service2Service3Subscriber::class.java)

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(Service2Aggregate::class, "service1::service2-subscriber") {
            `when`(Service2ProcessedEvent::class) { event ->
                val id = getSagaId(event)

                var aggregationOptional: Optional<Aggregation>
                try {
                    aggregationOptional = aggregationRepository.findById(id)
                    if (!aggregationOptional.isPresent) {
                        val service2 = Service2Aggregation(
                            event.service2Id,
                            event.sagaContext
                        )
                        aggregationRepository.save(
                            Aggregation(
                                id,
                                service2,
                                null
                            )
                        )
                    }
                } catch (e: DuplicateKeyException) {
                    logger.warn("Optimistic lock exception")
                }

                aggregationOptional = aggregationRepository.findById(id)
                if (aggregationOptional.isPresent && aggregationOptional.get().service3 != null) {
                    val aggregation = aggregationOptional.get()
                    aggregation.service2 = Service2Aggregation(
                        event.service2Id,
                        event.sagaContext
                    )
                    aggregationRepository.save(aggregation)
                    val service3 = aggregation.service3
                    val service2 = aggregation.service2

                    val sagaContext = sagaManager
                        .withContextGiven(service2!!.sagaCtx + service3!!.sagaCtx)
                        .performSagaStep("SAGA_EXAMPLE", "finish saga")
                        .sagaContext

                    service1EsService.update(event.service2Id, sagaContext) {
                        it.finishSaga(event.service2Id, service3.id)
                    }
                }
            }
        }

        subscriptionsManager.createSubscriber(Service3Aggregate::class, "service1::service3-subscriber") {
            `when`(Service3ProcessedEvent::class) { event ->
                val id = getSagaId(event)

                var aggregationOptional: Optional<Aggregation>
                try {
                    aggregationOptional = aggregationRepository.findById(id)
                    if (!aggregationOptional.isPresent) {
                        val service3 = Service3Aggregation(
                            event.service3Id,
                            event.sagaContext
                        )
                        aggregationRepository.save(
                            Aggregation(
                                id,
                                null,
                                service3
                            )
                        )
                    }
                } catch (e: DuplicateKeyException) {
                    logger.warn("Optimistic lock exception")
                }

                aggregationOptional = aggregationRepository.findById(id)
                if (aggregationOptional.isPresent && aggregationOptional.get().service2 != null) {
                    val aggregation = aggregationOptional.get()
                    aggregation.service3 = Service3Aggregation(
                        event.service3Id,
                        event.sagaContext
                    )
                    aggregationRepository.save(aggregation)
                    val service3 = aggregation.service3
                    val service2 = aggregation.service2

                    val sagaContext = sagaManager
                        .withContextGiven(service3!!.sagaCtx + service2!!.sagaCtx)
                        .performSagaStep("SAGA_EXAMPLE", "finish saga")
                        .sagaContext

                    service1EsService.update(service3.id, sagaContext) {
                        it.finishSaga(service2.id, service3.id)
                    }
                }
            }
        }
    }

    private fun <A : Aggregate, E : Event<A>> getSagaId(event: E): UUID {
        return event.sagaContext.ctx["SAGA_EXAMPLE"]!!.sagaInstanceId
    }
}

@Document("aggregation-example")
data class Aggregation(
    @Id
    val sagaInstanceId: UUID,
    var service2: Service2Aggregation?,
    var service3: Service3Aggregation?,
    @Version
    val version: Int = 0
)

data class Service2Aggregation(
    val id: UUID,
    val sagaCtx: SagaContext
)

data class Service3Aggregation(
    val id: UUID,
    val sagaCtx: SagaContext
)

@Repository
interface AggregationRepository : MongoRepository<Aggregation, UUID>