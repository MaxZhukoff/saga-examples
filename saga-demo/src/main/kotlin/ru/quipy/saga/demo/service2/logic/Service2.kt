package ru.quipy.saga.demo.service2.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.saga.demo.service2.api.Service2ProcessedEvent
import ru.quipy.saga.demo.service2.api.Service2Aggregate
import java.util.*

class Service2 : AggregateState<UUID, Service2Aggregate> {
    private lateinit var service2Id: UUID

    override fun getId() = service2Id

    fun processService2(id: UUID): Service2ProcessedEvent {
        return Service2ProcessedEvent(id)
    }

    @StateTransitionFunc
    fun processService2(event: Service2ProcessedEvent) {
        service2Id = event.service2Id
    }
}