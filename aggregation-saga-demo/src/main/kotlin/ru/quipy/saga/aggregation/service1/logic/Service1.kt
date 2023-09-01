package ru.quipy.saga.aggregation.service1.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.saga.aggregation.service1.api.Service1Aggregate
import ru.quipy.saga.aggregation.service1.api.Service1FinishedEvent
import ru.quipy.saga.aggregation.service1.api.Service1OptCheckedEvent
import ru.quipy.saga.aggregation.service1.api.Service1StartedEvent
import java.util.*

class Service1 : AggregateState<UUID, Service1Aggregate> {
    private lateinit var service1Id: UUID
    private var optStepCheck = false

    override fun getId() = service1Id

    fun startSaga(id: UUID = UUID.randomUUID()): Service1StartedEvent {
        return Service1StartedEvent(id)
    }

    fun finishSaga(id2: UUID, id3: UUID): Service1FinishedEvent {
        return Service1FinishedEvent(service1Id, id2, id3)
    }

    fun optStepSagaProcess(id: UUID): Service1OptCheckedEvent {
        return Service1OptCheckedEvent(id)
    }

    @StateTransitionFunc
    fun startSaga(event: Service1StartedEvent) {
        service1Id = event.service1Id
    }

    @StateTransitionFunc
    fun finishSaga(event: Service1FinishedEvent) {
    }

    @StateTransitionFunc
    fun optStepSagaProcess(event: Service1OptCheckedEvent) {
        optStepCheck = true
    }

}