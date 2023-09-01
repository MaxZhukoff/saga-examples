package ru.quipy.saga.aggregation.service1.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.aggregation.service1.api.Service1Aggregate
import ru.quipy.saga.aggregation.service1.logic.Service1
import ru.quipy.saga.aggregation.service4.api.Service4Aggregate
import ru.quipy.saga.aggregation.service4.api.Service4ProcessedEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class Service4Subscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val service1EsService: EventSourcingService<UUID, Service1Aggregate, Service1>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(Service4Aggregate::class, "service1::service4-subscriber") {
            `when`(Service4ProcessedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("SAGA_EXAMPLE", "process opt step after service4")
                    .sagaContext

                service1EsService.update(event.service4Id, sagaContext) {
                    it.optStepSagaProcess(event.service4Id)
                }
            }
        }
    }
}