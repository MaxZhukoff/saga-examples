package ru.quipy.saga.defaultsimple

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DefaultSagaDemoApplication

fun main(args: Array<String>) {
    runApplication<DefaultSagaDemoApplication>(*args)
}
