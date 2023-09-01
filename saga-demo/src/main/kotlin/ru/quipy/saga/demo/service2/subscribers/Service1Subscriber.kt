package ru.quipy.saga.demo.service2.subscribers

import org.springframework.stereotype.Component

import ru.quipy.core.EventSourcingService
import ru.quipy.saga.demo.service1.api.Service1StartedEvent
import ru.quipy.saga.demo.service1.api.Service1Aggregate
import ru.quipy.saga.demo.service2.api.Service2Aggregate
import ru.quipy.saga.demo.service2.logic.Service2
import ru.quipy.saga.SagaManager
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class Service1Subscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val service2EsService: EventSourcingService<UUID, Service2Aggregate, Service2>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(Service1Aggregate::class, "service2::service1-subscriber") {
            `when`(Service1StartedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("SAGA_EXAMPLE", "process service2")
                    .sagaContext

                service2EsService.create(sagaContext) {
                    it.processService2(event.service1Id)
                }
            }
        }
    }
}