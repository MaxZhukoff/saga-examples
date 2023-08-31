package ru.quipy.saga.simple.flights.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.simple.flights.api.FlightAggregate
import ru.quipy.saga.simple.flights.logic.Flight
import ru.quipy.saga.simple.payment.api.PaymentAggregate
import ru.quipy.saga.simple.payment.api.PaymentSucceededEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class PaymentSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val flightEsService: EventSourcingService<UUID, FlightAggregate, Flight>,
    private val sagaManager: SagaManager
) {
    val tripSagaName = "TRIP_RESERVATION"

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(PaymentAggregate::class, "flights::payment-subscriber") {
            `when`(PaymentSucceededEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep(tripSagaName, "reservation flight")
                    .sagaContext

                flightEsService.create(sagaContext) {
                    it.reserveFlight(sagaContext.ctx[tripSagaName]!!.sagaStepId, event.tripId, event.paymentId)
                }
            }
        }
    }
}