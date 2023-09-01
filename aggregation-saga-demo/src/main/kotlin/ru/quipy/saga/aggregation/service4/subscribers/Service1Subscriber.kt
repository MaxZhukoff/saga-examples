package ru.quipy.saga.aggregation.service4.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.aggregation.service1.api.Service1Aggregate
import ru.quipy.saga.aggregation.service1.api.Service1StartedEvent
import ru.quipy.saga.aggregation.service4.api.Service4Aggregate
import ru.quipy.saga.aggregation.service4.logic.Service4
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class Service1Subscriber4(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val service4EsService: EventSourcingService<UUID, Service4Aggregate, Service4>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(Service1Aggregate::class, "service4::service1-subscriber") {
            `when`(Service1StartedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("SAGA_EXAMPLE", "process service4")
                    .sagaContext

                service4EsService.create(sagaContext) { it.processService4(event.service1Id) }
            }
        }
    }
}