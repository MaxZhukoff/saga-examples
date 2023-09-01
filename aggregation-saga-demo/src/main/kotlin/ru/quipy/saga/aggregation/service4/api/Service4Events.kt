package ru.quipy.saga.aggregation.service4.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val SERVICE4_PROCESSED = "SERVICE4_PROCESSED_EVENT"

@DomainEvent(SERVICE4_PROCESSED)
data class Service4ProcessedEvent(
    val service4Id: UUID
) : Event<Service4Aggregate>(
    name = SERVICE4_PROCESSED
)