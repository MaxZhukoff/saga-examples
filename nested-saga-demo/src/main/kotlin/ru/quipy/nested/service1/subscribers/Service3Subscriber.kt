package ru.quipy.nested.service1.subscribers

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
import ru.quipy.saga.SagaManager
import ru.quipy.nested.service1.api.Service1Processed2Event
import ru.quipy.nested.service1.api.Service1Aggregate
import ru.quipy.nested.service1.logic.Service1
import ru.quipy.nested.service3.api.Service3ProcessedEvent
import ru.quipy.nested.service3.api.Service3Aggregate
import ru.quipy.saga.SagaContext
import ru.quipy.saga.plus
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class Service3Subscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val service1ProjectionRepository: Service1ProjectionRepository,
    private val service1EsService: EventSourcingService<UUID, Service1Aggregate, Service1>,
    private val sagaManager: SagaManager
) {
    private val logger: Logger = LoggerFactory.getLogger(Service3Subscriber::class.java)

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(Service1Aggregate::class, "service1-self-subscriber") {
            `when`(Service1Processed2Event::class) { event ->
                val id = getSagaId(event)

                var aggregationOptional: Optional<Aggregation>
                try {
                    aggregationOptional = service1ProjectionRepository.findById(id)
                    if (!aggregationOptional.isPresent) {
                        val service1Aggregation = Service1Aggregation(event.service1Id, event.sagaContext)
                        service1ProjectionRepository.save(Aggregation(id, service1Aggregation, null))
                    }
                } catch (e: DuplicateKeyException) {
                    logger.warn("Optimistic lock exception")
                }

                aggregationOptional = service1ProjectionRepository.findById(id)
                if (aggregationOptional.isPresent && aggregationOptional.get().service3 != null) {
                    val aggregation = aggregationOptional.get()
                    aggregation.service1 = Service1Aggregation(event.id, event.sagaContext)
                    service1ProjectionRepository.save(aggregation)

                    val service1 = aggregation.service1
                    val service3 = aggregation.service3

                    val sagaContext = sagaManager
                        .withContextGiven(service1!!.sagaCtx + service3!!.sagaCtx)
                        .performSagaStep("SAGA_EXAMPLE", "finish saga")
                        .sagaContext

                    service1EsService.update(service1.id, sagaContext) {
                        it.finishSaga(service1.id, service3.id)
                    }
                }

            }
        }

        subscriptionsManager.createSubscriber(Service3Aggregate::class, "service1::service3-subscriber") {
            `when`(Service3ProcessedEvent::class) { event ->
                val id = getSagaId(event)

                var aggregationOptional: Optional<Aggregation>
                try {
                    aggregationOptional = service1ProjectionRepository.findById(id)
                    if (!aggregationOptional.isPresent) {
                        val service3Aggregation = Service3Aggregation(event.service3Id, event.sagaContext)
                        service1ProjectionRepository.save(Aggregation(id, null, service3Aggregation))
                    }
                } catch (e: DuplicateKeyException) {
                    logger.warn("Optimistic lock exception")
                }


                aggregationOptional = service1ProjectionRepository.findById(id)
                if (aggregationOptional.isPresent && aggregationOptional.get().service1 != null) {
                    val aggregation = aggregationOptional.get()
                    aggregation.service3 = Service3Aggregation(event.id, event.sagaContext)
                    service1ProjectionRepository.save(aggregation)

                    val service1 = aggregation.service1
                    val service3 = aggregation.service3

                    val sagaContext = sagaManager
                        .withContextGiven(service1!!.sagaCtx + service3!!.sagaCtx)
                        .performSagaStep("SAGA_EXAMPLE", "finish saga")
                        .sagaContext

                    service1EsService.update(service1.id, sagaContext) {
                        it.finishSaga(service1.id, service3.id)
                    }
                }
            }
        }
    }

    private fun <A : Aggregate, E : Event<A>> getSagaId(event: E): UUID {
        return event.sagaContext.ctx["SAGA_EXAMPLE"]!!.sagaInstanceId
    }
}

@Document("nested-example")
data class Aggregation(
    @Id
    val sagaInstanceId: UUID,
    var service1: Service1Aggregation?,
    var service3: Service3Aggregation?,
    @Version
    val version: Int = 0
)

data class Service1Aggregation(
    val id: UUID,
    val sagaCtx: SagaContext
)

data class Service3Aggregation(
    val id: UUID,
    val sagaCtx: SagaContext
)

@Repository
interface Service1ProjectionRepository : MongoRepository<Aggregation, UUID>