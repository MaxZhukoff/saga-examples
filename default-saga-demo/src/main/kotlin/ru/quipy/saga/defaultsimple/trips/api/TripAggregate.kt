package ru.quipy.saga.defaultsimple.trips.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("trips-default")
class TripAggregate : Aggregate