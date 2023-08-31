package ru.quipy.saga.defaultsimple.payment.subscribers

import org.springframework.stereotype.Component

import ru.quipy.core.EventSourcingService
import ru.quipy.saga.defaultsimple.payment.api.PaymentAggregate
import ru.quipy.saga.defaultsimple.payment.logic.Payment
import ru.quipy.saga.defaultsimple.trips.api.TripAggregate
import ru.quipy.saga.defaultsimple.trips.api.TripReservationStartedEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class TripSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val paymentEsService: EventSourcingService<UUID, PaymentAggregate, Payment>,
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(TripAggregate::class, "payment::trips-default-subscriber") {
            `when`(TripReservationStartedEvent::class) { event ->

                paymentEsService.create(event.sagaContext) {
                    it.processPayment(event.tripId, event.cost)
                }
            }
        }
    }
}