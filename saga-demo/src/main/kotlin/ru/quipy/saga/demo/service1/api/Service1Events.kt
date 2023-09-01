package ru.quipy.saga.demo.service1.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val SERVICE1_STARTED = "SERVICE1_STARTED_EVENT"
const val SERVICE1_PROCESSED = "SERVICE1_PROCESSED_EVENT"

@DomainEvent(SERVICE1_STARTED)
data class Service1StartedEvent(
    val service1Id: UUID
) : Event<Service1Aggregate>(
    name = SERVICE1_STARTED
)

@DomainEvent(SERVICE1_PROCESSED)
data class Service1ProcessedEvent(
    val service1Id: UUID
) : Event<Service1Aggregate>(
    name = SERVICE1_PROCESSED
)