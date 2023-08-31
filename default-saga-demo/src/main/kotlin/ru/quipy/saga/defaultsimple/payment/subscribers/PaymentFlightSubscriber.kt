package ru.quipy.saga.defaultsimple.payment.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.defaultsimple.flights.api.FlightAggregate
import ru.quipy.saga.defaultsimple.flights.api.FlightReservationCanceledEvent
import ru.quipy.saga.defaultsimple.payment.api.PaymentAggregate
import ru.quipy.saga.defaultsimple.payment.logic.Payment
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class PaymentFlightSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val paymentEsService: EventSourcingService<UUID, PaymentAggregate, Payment>,
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(FlightAggregate::class, "payment::flights-default-subscriber") {
            `when`(FlightReservationCanceledEvent::class) { event ->

                paymentEsService.update(event.paymentId, event.sagaContext) {
                    it.refundPayment()
                }
            }
        }
    }
}