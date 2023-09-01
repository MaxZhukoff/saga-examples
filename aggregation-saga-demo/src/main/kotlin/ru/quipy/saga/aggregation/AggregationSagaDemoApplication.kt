package ru.quipy.saga.aggregation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AggregationSagaDemoApplication

fun main(args: Array<String>) {
    runApplication<AggregationSagaDemoApplication>(*args)
}
