package ru.quipy.saga.defaultsimple.trips.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.defaultsimple.payment.api.PaymentAggregate
import ru.quipy.saga.defaultsimple.payment.api.PaymentFailedEvent
import ru.quipy.saga.defaultsimple.payment.api.PaymentRefundedEvent
import ru.quipy.saga.defaultsimple.trips.api.TripAggregate
import ru.quipy.saga.defaultsimple.trips.logic.Trip
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class PaymentTripSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val tripEsService: EventSourcingService<UUID, TripAggregate, Trip>,
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(PaymentAggregate::class, "trips::payment-default-subscriber") {
            `when`(PaymentFailedEvent::class) { event ->

                tripEsService.update(event.tripId, event.sagaContext) {
                    it.cancelTrip(event.paymentId)
                }
            }

            `when`(PaymentRefundedEvent::class) { event ->

                tripEsService.update(event.tripId, event.sagaContext) {
                    it.cancelTrip(event.paymentId)
                }
            }
        }
    }
}