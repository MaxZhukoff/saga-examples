package ru.quipy.nested.service1.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val SERVICE1_STARTED = "SERVICE1_STARTED_EVENT"
const val SERVICE1_PROCESSED1 = "SERVICE1_PROCESSED1_EVENT"
const val SERVICE1_PROCESSED2 = "SERVICE1_PROCESSED2_EVENT"
const val SERVICE1_FINISHED = "SERVICE1_FINISHED_EVENT"

@DomainEvent(SERVICE1_STARTED)
data class Service1StartedEvent(
    val service1Id: UUID
) : Event<Service1Aggregate>(
    name = SERVICE1_STARTED
)

@DomainEvent(SERVICE1_PROCESSED1)
data class Service1Processed1Event(
    val service1Id: UUID
) : Event<Service1Aggregate>(
    name = SERVICE1_PROCESSED1
)

@DomainEvent(SERVICE1_PROCESSED2)
data class Service1Processed2Event(
    val service1Id: UUID
) : Event<Service1Aggregate>(
    name = SERVICE1_PROCESSED2
)


@DomainEvent(SERVICE1_FINISHED)
data class Service1FinishedEvent(
    val service1Id: UUID,
    val service2Id: UUID,
    val service3Id: UUID,
) : Event<Service1Aggregate>(
    name = SERVICE1_FINISHED,
)
