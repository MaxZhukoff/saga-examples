package ru.quipy.saga.defaultsimple.trips.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.defaultsimple.trips.api.TripAggregate
import ru.quipy.saga.defaultsimple.trips.api.TripReservationStartedEvent
import ru.quipy.saga.defaultsimple.trips.logic.Trip
import java.util.*

@RestController
class TripController(
    val tripEsService: EventSourcingService<UUID, TripAggregate, Trip>,
) {

    @PostMapping
    fun reserveTrip(@RequestParam cost: Int): TripReservationStartedEvent {

        return tripEsService.create {
            it.tripStartReservation(cost)
        }
    }

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: UUID): Trip? {
        return tripEsService.getState(id)
    }
}