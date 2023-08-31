package ru.quipy.saga.defaultsimple.flights.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val FLIGHT_RESERVED = "FLIGHT_RESERVED_EVENT"
const val FLIGHTS_RESERVATION_FAILED = "FLIGHTS_RESERVATION_FAILED_EVENT"

@DomainEvent(FLIGHT_RESERVED)
data class FlightReservedEvent(
    val flightId: UUID,
    val tripId: UUID,
    val paymentId: UUID
) : Event<FlightAggregate>(
    name = FLIGHT_RESERVED,
)

@DomainEvent(FLIGHTS_RESERVATION_FAILED)
data class FlightReservationCanceledEvent(
    val flightId: UUID,
    val tripId: UUID,
    val paymentId: UUID
) : Event<FlightAggregate>(
    name = FLIGHTS_RESERVATION_FAILED,
)