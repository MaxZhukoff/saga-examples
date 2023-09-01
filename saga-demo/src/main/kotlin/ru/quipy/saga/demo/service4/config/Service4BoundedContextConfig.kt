package ru.quipy.saga.demo.service4.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.saga.demo.service4.api.Service4Aggregate
import ru.quipy.saga.demo.service4.logic.Service4
import java.util.*

@Configuration
class Service4BoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun service4EsService(): EventSourcingService<UUID, Service4Aggregate, Service4> =
        eventSourcingServiceFactory.create()
}