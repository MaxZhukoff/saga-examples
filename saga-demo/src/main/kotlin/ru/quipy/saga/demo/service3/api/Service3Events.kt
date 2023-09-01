package ru.quipy.saga.demo.service3.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val SERVICE3_PROCESSED1 = "SERVICE3_PROCESSED1_EVENT"
const val SERVICE3_PROCESSED2 = "SERVICE3_PROCESSED2_EVENT"

@DomainEvent(SERVICE3_PROCESSED1)
data class Service3Processed1Event(
    val service3Id: UUID
) : Event<Service3Aggregate>(
    name = SERVICE3_PROCESSED1
)

@DomainEvent(SERVICE3_PROCESSED2)
data class Service3Processed2Event(
    val service3Id: UUID
) : Event<Service3Aggregate>(
    name = SERVICE3_PROCESSED2
)