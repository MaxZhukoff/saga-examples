package ru.quipy.saga.aggregation.service3.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.saga.aggregation.service3.api.Service3Aggregate
import ru.quipy.saga.aggregation.service3.logic.Service3
import java.util.*

@Configuration
class Service3BoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun service3EsService(): EventSourcingService<UUID, Service3Aggregate, Service3> =
        eventSourcingServiceFactory.create()
}