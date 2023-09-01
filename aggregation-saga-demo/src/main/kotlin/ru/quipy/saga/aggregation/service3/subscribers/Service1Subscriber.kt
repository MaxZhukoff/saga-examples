package ru.quipy.saga.aggregation.service3.subscribers

import org.springframework.stereotype.Component

import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.aggregation.service1.api.Service1Aggregate
import ru.quipy.saga.aggregation.service1.api.Service1StartedEvent
import ru.quipy.saga.aggregation.service3.api.Service3Aggregate
import ru.quipy.saga.aggregation.service3.logic.Service3
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class Service1Subscriber3(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val service3EsService: EventSourcingService<UUID, Service3Aggregate, Service3>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(Service1Aggregate::class, "service3::service1-subscriber") {
            `when`(Service1StartedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("SAGA_EXAMPLE", "process service3")
                    .sagaContext

                service3EsService.create(sagaContext) { it.processService3(event.service1Id) }
            }
        }
    }
}