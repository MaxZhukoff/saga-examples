package ru.quipy.saga.simple.flights.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("flights")
class FlightAggregate : Aggregate