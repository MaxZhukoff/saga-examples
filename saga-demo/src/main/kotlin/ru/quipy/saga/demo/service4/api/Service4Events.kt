package ru.quipy.saga.demo.service4.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val SERVICE4_PROCESSED1 = "SERVICE4_PROCESSED1_EVENT"
const val SERVICE4_PROCESSED2 = "SERVICE4_PROCESSED2_EVENT"

@DomainEvent(SERVICE4_PROCESSED1)
data class Service4Processed1Event(
    val service4Id: UUID
) : Event<Service4Aggregate>(
    name = SERVICE4_PROCESSED1
)

@DomainEvent(SERVICE4_PROCESSED2)
data class Service4Processed2Event(
    val service4Id: UUID
) : Event<Service4Aggregate>(
    name = SERVICE4_PROCESSED2
)