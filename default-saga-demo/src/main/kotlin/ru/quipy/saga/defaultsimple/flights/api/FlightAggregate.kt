package ru.quipy.saga.defaultsimple.flights.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("flights-default")
class FlightAggregate : Aggregate