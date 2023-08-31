package ru.quipy.saga.defaultsimple.flights.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.defaultsimple.flights.api.FlightAggregate
import ru.quipy.saga.defaultsimple.flights.logic.Flight
import ru.quipy.saga.defaultsimple.payment.api.PaymentAggregate
import ru.quipy.saga.defaultsimple.payment.api.PaymentSucceededEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class PaymentSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val flightEsService: EventSourcingService<UUID, FlightAggregate, Flight>,
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(PaymentAggregate::class, "flights::payment-default-subscriber") {
            `when`(PaymentSucceededEvent::class) { event ->

                flightEsService.create(event.sagaContext) {
                    it.reserveFlight(event.tripId, event.paymentId)
                }
            }
        }
    }
}