package ru.quipy.nested.service3.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val SERVICE3_PROCESSED = "SERVICE3_PROCESSED_EVENT"

@DomainEvent(SERVICE3_PROCESSED)
data class Service3ProcessedEvent(
    val service3Id: UUID
) : Event<Service3Aggregate>(
    name = SERVICE3_PROCESSED
)