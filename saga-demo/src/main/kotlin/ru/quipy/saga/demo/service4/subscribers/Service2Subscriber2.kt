package ru.quipy.saga.demo.service4.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.demo.service2.api.Service2ProcessedEvent
import ru.quipy.saga.demo.service2.api.Service2Aggregate
import ru.quipy.saga.demo.service4.api.Service4Aggregate
import ru.quipy.saga.demo.service4.logic.Service4
import ru.quipy.saga.SagaManager
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class Service2Subscriber2(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val service4EsService: EventSourcingService<UUID, Service4Aggregate, Service4>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(Service2Aggregate::class, "service4::service2-subscriber") {
            `when`(Service2ProcessedEvent::class) { event ->
                var sagaContext = sagaManager
                    .launchSaga("NESTED_SAGA_EXAMPLE", "process1 service4")
                    .sagaContext
                val event1 = service4EsService.create(sagaContext) { it.process1Service4(event.service2Id) }

                sagaContext = sagaManager
                    .withContextGiven(event1.sagaContext)
                    .performSagaStep("NESTED_SAGA_EXAMPLE", "process2 service4")
                    .sagaContext
                service4EsService.update(event1.service4Id, sagaContext) { it.process2Service4() }
            }
        }
    }
}