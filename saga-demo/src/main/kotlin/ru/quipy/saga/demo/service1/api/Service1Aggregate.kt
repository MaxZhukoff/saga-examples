package ru.quipy.saga.demo.service1.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("service1")
class Service1Aggregate : Aggregate