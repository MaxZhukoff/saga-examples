package ru.quipy.saga.simple.payment.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val PAYMENT_SUCCEEDED = "PAYMENT_SUCCEEDED_EVENT"
const val PAYMENT_FAILED = "PAYMENT_FAILED_EVENT"
const val PAYMENT_REFUNDED = "PAYMENT_REFUNDED_EVENT"

@DomainEvent(PAYMENT_SUCCEEDED)
data class PaymentSucceededEvent(
    val paymentId: UUID,
    val tripId: UUID,
    val amount: Int
) : Event<PaymentAggregate>(
    name = PAYMENT_SUCCEEDED,
)

@DomainEvent(PAYMENT_FAILED)
data class PaymentFailedEvent(
    val paymentId: UUID,
    val tripId: UUID,
    val amount: Int
) : Event<PaymentAggregate>(
    name = PAYMENT_FAILED,
)

@DomainEvent(PAYMENT_REFUNDED)
data class PaymentRefundedEvent(
    val paymentId: UUID,
    val tripId: UUID
) : Event<PaymentAggregate>(
    name = PAYMENT_REFUNDED,
)
