package ru.quipy.saga.simple.payment.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import ru.quipy.saga.simple.payment.api.PaymentAggregate
import ru.quipy.saga.simple.payment.api.PaymentFailedEvent
import ru.quipy.saga.simple.payment.api.PaymentRefundedEvent
import ru.quipy.saga.simple.payment.api.PaymentSucceededEvent
import java.util.*

class Payment : AggregateState<UUID, PaymentAggregate> {
    private lateinit var paymentId: UUID
    private lateinit var tripId: UUID
    private var amount: Int = 0
    private var failed: Boolean = false
    private var refund: Boolean = false

    override fun getId() = paymentId

    fun getAmount() = amount

    fun isFailed() = failed

    fun isRefund() = refund

    fun processPayment(id: UUID, tripId: UUID, amount: Int): Event<PaymentAggregate> {
        return if (amount > 0)
            PaymentSucceededEvent(id, tripId, amount)
        else
            PaymentFailedEvent(id, tripId, amount)
    }

    fun refundPayment(): PaymentRefundedEvent {
        return PaymentRefundedEvent(paymentId, tripId)
    }

    @StateTransitionFunc
    fun processPayment(event: PaymentSucceededEvent) {
        paymentId = event.paymentId
        tripId = event.tripId
        amount = event.amount
    }

    @StateTransitionFunc
    fun canselPayment(event: PaymentFailedEvent) {
        paymentId = event.paymentId
        tripId = event.tripId
        amount = event.amount
        failed = true
    }

    @StateTransitionFunc
    fun refundPayment(event: PaymentRefundedEvent) {
        refund = true
    }
}