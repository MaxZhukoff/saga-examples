package ru.quipy.saga.simple.trips.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.simple.flights.api.FlightAggregate
import ru.quipy.saga.simple.flights.api.FlightReservedEvent
import ru.quipy.saga.simple.trips.api.TripAggregate
import ru.quipy.saga.simple.trips.logic.Trip
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class FlightSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val tripEsService: EventSourcingService<UUID, TripAggregate, Trip>,
    private val sagaManager: SagaManager
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(FlightAggregate::class, "trips::flights-subscriber") {
            `when`(FlightReservedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep("TRIP_RESERVATION", "finish reservation")
                    .sagaContext

                tripEsService.update(event.tripId, sagaContext) {
                    it.confirmTrip(event.paymentId, event.flightId)
                }
            }
        }
    }
}