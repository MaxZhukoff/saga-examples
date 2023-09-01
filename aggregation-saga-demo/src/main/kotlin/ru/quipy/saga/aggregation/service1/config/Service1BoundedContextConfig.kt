package ru.quipy.saga.aggregation.service1.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.saga.aggregation.service1.api.Service1Aggregate
import ru.quipy.saga.aggregation.service1.logic.Service1
import java.util.*

@Configuration
class Service1BoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun service1EsService(): EventSourcingService<UUID, Service1Aggregate, Service1> =
        eventSourcingServiceFactory.create()
}