package ru.quipy.saga.demo.service1.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.saga.demo.service1.api.Service1ProcessedEvent
import ru.quipy.saga.demo.service1.api.Service1StartedEvent
import ru.quipy.saga.demo.service1.api.Service1Aggregate
import java.util.*

class Service1 : AggregateState<UUID, Service1Aggregate> {
    private lateinit var service1Id: UUID

    override fun getId() = service1Id

    fun startSaga(id: UUID = UUID.randomUUID()): Service1StartedEvent {
        return Service1StartedEvent(id)
    }

    fun processService1(id: UUID): Service1ProcessedEvent {
        return Service1ProcessedEvent(id)
    }

    @StateTransitionFunc
    fun startSaga(event: Service1StartedEvent) {
        service1Id = event.service1Id
    }

    @StateTransitionFunc
    fun processService1(event: Service1ProcessedEvent) {
    }
}