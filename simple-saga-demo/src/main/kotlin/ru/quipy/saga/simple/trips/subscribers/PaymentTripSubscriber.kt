package ru.quipy.saga.simple.trips.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.simple.payment.api.PaymentAggregate
import ru.quipy.saga.simple.payment.api.PaymentFailedEvent
import ru.quipy.saga.simple.payment.api.PaymentRefundedEvent
import ru.quipy.saga.simple.trips.api.TripAggregate
import ru.quipy.saga.simple.trips.logic.Trip
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class PaymentTripSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val tripEsService: EventSourcingService<UUID, TripAggregate, Trip>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(PaymentAggregate::class, "trips::payment-subscriber") {
            `when`(PaymentFailedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("TRIP_RESERVATION", "reservation failed").sagaContext

                tripEsService.update(event.tripId, sagaContext) {
                    it.cancelTrip(event.paymentId)
                }
            }

            `when`(PaymentRefundedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("TRIP_RESERVATION", "reservation failed").sagaContext

                tripEsService.update(event.tripId, sagaContext) {
                    it.cancelTrip(event.paymentId)
                }
            }
        }
    }
}