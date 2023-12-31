package ru.quipy.saga.simple.flights.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.saga.simple.flights.api.FlightAggregate
import ru.quipy.saga.simple.flights.logic.Flight
import java.util.*

@Configuration
class FlightBoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun flightEsService(): EventSourcingService<UUID, FlightAggregate, Flight> =
        eventSourcingServiceFactory.create()
}