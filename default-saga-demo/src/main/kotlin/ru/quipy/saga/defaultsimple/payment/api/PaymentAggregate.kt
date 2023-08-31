package ru.quipy.saga.defaultsimple.payment.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("payment-default")
class PaymentAggregate : Aggregate