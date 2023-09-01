package ru.quipy.saga.demo.service3.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.saga.demo.service3.api.Service3Processed1Event
import ru.quipy.saga.demo.service3.api.Service3Processed2Event
import ru.quipy.saga.demo.service3.api.Service3Aggregate
import java.util.*

class Service3 : AggregateState<UUID, Service3Aggregate> {
    private lateinit var service3Id: UUID

    override fun getId() = service3Id

    fun process1Service3(id: UUID): Service3Processed1Event {
        return Service3Processed1Event(id)
    }

    fun process2Service3(): Service3Processed2Event {
        return Service3Processed2Event(getId())
    }

    @StateTransitionFunc
    fun process1Service3(event: Service3Processed1Event) {
        service3Id = event.service3Id
    }

    @StateTransitionFunc
    fun process2Service3(event: Service3Processed2Event) {
    }
}