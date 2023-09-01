package ru.quipy.saga.aggregation.service4.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("service4")
class Service4Aggregate : Aggregate