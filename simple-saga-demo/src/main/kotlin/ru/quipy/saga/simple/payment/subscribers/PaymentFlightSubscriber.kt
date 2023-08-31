package ru.quipy.saga.simple.payment.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.simple.flights.api.FlightAggregate
import ru.quipy.saga.simple.flights.api.FlightReservationCanceledEvent
import ru.quipy.saga.simple.payment.api.PaymentAggregate
import ru.quipy.saga.simple.payment.logic.Payment
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class PaymentFlightSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val paymentEsService: EventSourcingService<UUID, PaymentAggregate, Payment>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(FlightAggregate::class, "payment::flights-subscriber") {
            `when`(FlightReservationCanceledEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("TRIP_RESERVATION", "refund payment")
                    .sagaContext

                paymentEsService.update(event.paymentId, sagaContext) {
                    it.refundPayment()
                }
            }
        }
    }
}