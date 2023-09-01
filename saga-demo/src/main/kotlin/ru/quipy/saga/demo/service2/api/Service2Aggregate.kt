package ru.quipy.saga.demo.service2.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("service2")
class Service2Aggregate : Aggregate