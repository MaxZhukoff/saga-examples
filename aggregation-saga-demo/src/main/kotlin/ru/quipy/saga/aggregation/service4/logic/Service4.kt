package ru.quipy.saga.aggregation.service4.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.saga.aggregation.service4.api.Service4Aggregate
import ru.quipy.saga.aggregation.service4.api.Service4ProcessedEvent
import java.util.*

class Service4 : AggregateState<UUID, Service4Aggregate> {
    private lateinit var service4Id: UUID

    override fun getId() = service4Id

    fun processService4(id: UUID): Service4ProcessedEvent {
        return Service4ProcessedEvent(id)
    }

    @StateTransitionFunc
    fun processService4(event: Service4ProcessedEvent) {
        service4Id = event.service4Id
    }
}