package ru.quipy.nested

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NestedSagaDemoApplication

fun main(args: Array<String>) {
    runApplication<NestedSagaDemoApplication>(*args)
}
