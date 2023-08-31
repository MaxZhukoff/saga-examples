package ru.quipy.saga.defaultsimple.trips.subscribers

import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.defaultsimple.flights.api.FlightAggregate
import ru.quipy.saga.defaultsimple.flights.api.FlightReservedEvent
import ru.quipy.saga.defaultsimple.trips.api.TripAggregate
import ru.quipy.saga.defaultsimple.trips.logic.Trip
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class FlightSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val tripEsService: EventSourcingService<UUID, TripAggregate, Trip>,
) {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(FlightAggregate::class, "trips::flights-default-subscriber") {
            `when`(FlightReservedEvent::class) { event ->

                tripEsService.update(event.tripId, event.sagaContext) {
                    it.confirmTrip(event.paymentId, event.flightId)
                }
            }
        }
    }
}