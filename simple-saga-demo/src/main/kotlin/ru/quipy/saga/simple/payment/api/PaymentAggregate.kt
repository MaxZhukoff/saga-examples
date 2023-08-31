package ru.quipy.saga.simple.payment.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("payment")
class PaymentAggregate : Aggregate