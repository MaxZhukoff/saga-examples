package ru.quipy.saga.simple.trips.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val TRIP_RESERVATION_STARTED = "TRIP_RESERVATION_STARTED_EVENT"
const val TRIP_RESERVATION_CONFIRMED = "TRIP_RESERVATION_CONFIRMED_EVENT"
const val TRIP_RESERVATION_CANCELED = "TRIP_RESERVATION_CANCELED_EVENT"

@DomainEvent(TRIP_RESERVATION_STARTED)
data class TripReservationStartedEvent(
    val tripId: UUID,
    val cost: Int
) : Event<TripAggregate>(
    name = TRIP_RESERVATION_STARTED,
)

@DomainEvent(TRIP_RESERVATION_CANCELED)
data class TripReservationCanceledEvent(
    val tripId: UUID,
    val paymentId: UUID
) : Event<TripAggregate>(
    name = TRIP_RESERVATION_CANCELED,
)

@DomainEvent(TRIP_RESERVATION_CONFIRMED)
data class TripReservationConfirmedEvent(
    val tripId: UUID,
    val paymentId: UUID,
    val flightId: UUID
) : Event<TripAggregate>(
    name = TRIP_RESERVATION_CONFIRMED,
)
