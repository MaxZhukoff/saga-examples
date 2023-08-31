package ru.quipy.saga.simple

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleSagaDemoApplication

fun main(args: Array<String>) {
    runApplication<SimpleSagaDemoApplication>(*args)
}
