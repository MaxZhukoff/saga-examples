package ru.quipy.saga.aggregation.service3.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.saga.aggregation.service3.api.Service3Aggregate
import ru.quipy.saga.aggregation.service3.api.Service3ProcessedEvent
import java.util.*

class Service3 : AggregateState<UUID, Service3Aggregate> {
    private lateinit var service3Id: UUID

    override fun getId() = service3Id

    fun processService3(id: UUID): Service3ProcessedEvent {
        return Service3ProcessedEvent(id)
    }

    @StateTransitionFunc
    fun processService3(event: Service3ProcessedEvent) {
        service3Id = event.service3Id
    }
}