package ru.quipy.saga.simple.trips.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.saga.simple.trips.api.*
import java.util.*

class Trip : AggregateState<UUID, TripAggregate> {
    private lateinit var tripId: UUID
    private var flightId: UUID? = null
    private var paymentId: UUID? = null
    private var cost: Int = 0
    private var canceled: Boolean = false
    private var confirmed: Boolean = false

    override fun getId() = tripId

    fun getFlightId() = flightId

    fun getPaymentId() = paymentId

    fun getCost() = cost

    fun isCanceled() = canceled

    fun isConfirmed() = confirmed

    fun tripStartReservation(id: UUID, cost: Int): TripReservationStartedEvent {
        return TripReservationStartedEvent(id, cost)
    }

    fun cancelTrip(paymentId: UUID): TripReservationCanceledEvent {
        return TripReservationCanceledEvent(tripId, paymentId)
    }

    fun confirmTrip(paymentId: UUID, flightId: UUID): TripReservationConfirmedEvent {
        return TripReservationConfirmedEvent(tripId, paymentId, flightId)
    }


    @StateTransitionFunc
    fun tripStartReservation(event: TripReservationStartedEvent) {
        tripId = event.tripId
        cost = event.cost
    }

    @StateTransitionFunc
    fun cancelTrip(event: TripReservationCanceledEvent) {
        paymentId = event.paymentId
        canceled = true
    }

    @StateTransitionFunc
    fun confirmTrip(event: TripReservationConfirmedEvent) {
        paymentId = event.paymentId
        flightId = event.flightId
        confirmed = true
    }
}