package ru.quipy.saga.demo.service3.subscribers

import org.springframework.stereotype.Component

import ru.quipy.core.EventSourcingService
import ru.quipy.saga.demo.service2.api.Service2ProcessedEvent
import ru.quipy.saga.demo.service2.api.Service2Aggregate
import ru.quipy.saga.demo.service3.api.Service3Aggregate
import ru.quipy.saga.demo.service3.logic.Service3
import ru.quipy.saga.SagaManager
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class Service2Subscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val service3EsService: EventSourcingService<UUID, Service3Aggregate, Service3>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(Service2Aggregate::class, "service3::service2-subscriber") {
            `when`(Service2ProcessedEvent::class) { event ->
                var sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("SAGA_EXAMPLE", "process1 service3")
                    .sagaContext
                val event1 = service3EsService.create(sagaContext) { it.process1Service3(event.service2Id) }

                sagaContext = sagaManager
                    .withContextGiven(event1.sagaContext)
                    .performSagaStep("SAGA_EXAMPLE", "process2 service3")
                    .sagaContext
                service3EsService.update(event1.service3Id, sagaContext) { it.process2Service3() }
            }
        }
    }
}