package ru.quipy.saga.defaultsimple.flights.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import ru.quipy.saga.defaultsimple.flights.api.FlightAggregate
import ru.quipy.saga.defaultsimple.flights.api.FlightReservationCanceledEvent
import ru.quipy.saga.defaultsimple.flights.api.FlightReservedEvent
import java.util.*

class Flight : AggregateState<UUID, FlightAggregate> {
    private lateinit var flightId: UUID
    private lateinit var tripId: UUID
    private lateinit var paymentId: UUID
    private var canceled: Boolean = false

    override fun getId() = flightId

    fun getTripId() = tripId

    fun getPaymentId() = paymentId

    fun isCanceled() = canceled

    fun reserveFlight(tripId: UUID, paymentId: UUID, id: UUID = UUID.randomUUID()): Event<FlightAggregate> {
        return try {
            FlightReservedEvent(id, tripId, paymentId)
        } catch (e: Exception) {
            FlightReservationCanceledEvent(id, tripId, paymentId)
        }
    }

    @StateTransitionFunc
    fun reserveFlight(event: FlightReservedEvent) {
        flightId = event.flightId
        tripId = event.tripId
        paymentId = event.paymentId
    }

    @StateTransitionFunc
    fun canselFlight(event: FlightReservationCanceledEvent) {
        flightId = event.flightId
        tripId = event.tripId
        paymentId = event.paymentId
        canceled = true
    }
}