package ru.quipy.saga.demo.service3.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType("service3")
class Service3Aggregate : Aggregate