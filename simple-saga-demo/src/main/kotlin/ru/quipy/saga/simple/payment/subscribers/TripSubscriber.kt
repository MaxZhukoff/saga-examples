package ru.quipy.saga.simple.payment.subscribers

import org.springframework.stereotype.Component

import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.simple.payment.api.PaymentAggregate
import ru.quipy.saga.simple.payment.logic.Payment
import ru.quipy.saga.simple.trips.api.TripAggregate
import ru.quipy.saga.simple.trips.api.TripReservationStartedEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class TripSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val paymentEsService: EventSourcingService<UUID, PaymentAggregate, Payment>,
    private val sagaManager: SagaManager
) {
    val tripSagaName = "TRIP_RESERVATION"

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(TripAggregate::class, "payment::trips-subscriber") {
            `when`(TripReservationStartedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep(tripSagaName, "process payment")
                    .sagaContext

                paymentEsService.create(sagaContext) {
                    it.processPayment(sagaContext.ctx[tripSagaName]!!.sagaStepId, event.tripId, event.cost)
                }
            }
        }
    }
}