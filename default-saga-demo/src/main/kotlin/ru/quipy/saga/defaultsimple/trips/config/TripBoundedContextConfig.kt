package ru.quipy.saga.defaultsimple.trips.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.saga.defaultsimple.trips.api.TripAggregate
import ru.quipy.saga.defaultsimple.trips.logic.Trip
import java.util.*

@Configuration
class TripBoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun tripEsService(): EventSourcingService<UUID, TripAggregate, Trip> =
        eventSourcingServiceFactory.create()
}