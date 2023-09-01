package ru.quipy.saga.demo.service2.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val SERVICE2_PROCESSED = "SERVICE2_PROCESSED_EVENT"

@DomainEvent(SERVICE2_PROCESSED)
data class Service2ProcessedEvent(
    val service2Id: UUID
) : Event<Service2Aggregate>(
    name = SERVICE2_PROCESSED
)
