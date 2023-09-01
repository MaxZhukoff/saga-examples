package ru.quipy.saga.aggregation.service1.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val SERVICE1_STARTED = "SERVICE1_STARTED_EVENT"
const val SERVICE1_FINISHED = "SERVICE1_FINISHED_EVENT"
const val SERVICE1_OPT_CHECKED = "SERVICE1_OPT_CHECKED_EVENT"

@DomainEvent(SERVICE1_STARTED)
data class Service1StartedEvent(
    val service1Id: UUID
) : Event<Service1Aggregate>(
    name = SERVICE1_STARTED
)

@DomainEvent(SERVICE1_FINISHED)
data class Service1FinishedEvent(
    val service1Id: UUID,
    val service2Id: UUID,
    val service3Id: UUID,
) : Event<Service1Aggregate>(
    name = SERVICE1_FINISHED,
)

@DomainEvent(SERVICE1_OPT_CHECKED)
data class Service1OptCheckedEvent(
    val service1Id: UUID
) : Event<Service1Aggregate>(
    name = SERVICE1_OPT_CHECKED
)
