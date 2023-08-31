package ru.quipy.saga.simple.trips.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.simple.trips.api.TripAggregate
import ru.quipy.saga.simple.trips.api.TripReservationStartedEvent
import ru.quipy.saga.simple.trips.logic.Trip
import java.util.*

@RestController
class TripController(
    val tripEsService: EventSourcingService<UUID, TripAggregate, Trip>,
    val sagaManager: SagaManager,
) {
    val tripSagaName = "TRIP_RESERVATION"

    @PostMapping
    fun reserveTrip(@RequestParam cost: Int): TripReservationStartedEvent {
        val sagaContext = sagaManager
            .launchSaga(tripSagaName, "start reservation")
            .sagaContext

        return tripEsService.create(sagaContext) {
            it.tripStartReservation(sagaContext.ctx[tripSagaName]!!.sagaInstanceId, cost)
        }
    }

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: UUID): Trip? {
        return tripEsService.getState(id)
    }
}