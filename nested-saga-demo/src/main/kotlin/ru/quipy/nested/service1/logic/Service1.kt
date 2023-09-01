package ru.quipy.nested.service1.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.nested.service1.api.*
import java.util.*

class Service1 : AggregateState<UUID, Service1Aggregate> {
    private lateinit var service1Id: UUID

    override fun getId() = service1Id

    fun startSaga(id: UUID = UUID.randomUUID()): Service1StartedEvent {
        return Service1StartedEvent(id)
    }

    fun process1Saga(): Service1Processed1Event {
        return Service1Processed1Event(getId())
    }

    fun process2Saga(): Service1Processed2Event {
        return Service1Processed2Event(getId())
    }

    fun finishSaga(id2: UUID, id3: UUID): Service1FinishedEvent {
        return Service1FinishedEvent(getId(), id2, id3)
    }

    @StateTransitionFunc
    fun startSaga(event: Service1StartedEvent) {
        service1Id = event.service1Id
    }

    @StateTransitionFunc
    fun process1Saga(event: Service1Processed1Event) {
    }

    @StateTransitionFunc
    fun process2Saga(event: Service1Processed2Event) {
    }

    @StateTransitionFunc
    fun finishSaga(event: Service1FinishedEvent) {
    }
}