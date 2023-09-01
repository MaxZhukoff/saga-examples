package ru.quipy.saga.demo.service4.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.saga.demo.service4.api.Service4Processed1Event
import ru.quipy.saga.demo.service4.api.Service4Processed2Event
import ru.quipy.saga.demo.service4.api.Service4Aggregate
import java.util.*

class Service4 : AggregateState<UUID, Service4Aggregate> {
    private lateinit var service4Id: UUID

    override fun getId() = service4Id

    fun process1Service4(id: UUID): Service4Processed1Event {
        return Service4Processed1Event(id)
    }

    fun process2Service4(): Service4Processed2Event {
        return Service4Processed2Event(getId())
    }

    @StateTransitionFunc
    fun process1Service4(event: Service4Processed1Event) {
        service4Id = event.service4Id
    }

    @StateTransitionFunc
    fun process2Service4(event: Service4Processed2Event) {
    }
}