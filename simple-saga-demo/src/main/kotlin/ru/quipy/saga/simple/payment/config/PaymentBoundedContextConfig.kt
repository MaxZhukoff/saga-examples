package ru.quipy.saga.simple.payment.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.saga.simple.payment.api.PaymentAggregate
import ru.quipy.saga.simple.payment.logic.Payment
import java.util.*

@Configuration
class PaymentBoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun paymentEsService(): EventSourcingService<UUID, PaymentAggregate, Payment> =
        eventSourcingServiceFactory.create()
}