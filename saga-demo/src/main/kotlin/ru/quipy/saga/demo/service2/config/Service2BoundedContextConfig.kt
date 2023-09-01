package ru.quipy.saga.demo.service2.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.saga.demo.service2.api.Service2Aggregate
import ru.quipy.saga.demo.service2.logic.Service2
import java.util.*

@Configuration
class Service2BoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun service2EsService(): EventSourcingService<UUID, Service2Aggregate, Service2> =
        eventSourcingServiceFactory.create()
}