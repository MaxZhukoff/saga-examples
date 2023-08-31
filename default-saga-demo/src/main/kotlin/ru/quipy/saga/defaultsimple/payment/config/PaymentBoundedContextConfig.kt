package ru.quipy.saga.defaultsimple.payment.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.saga.defaultsimple.payment.api.PaymentAggregate
import ru.quipy.saga.defaultsimple.payment.logic.Payment
import java.util.*

@Configuration
class PaymentBoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun paymentEsService(): EventSourcingService<UUID, PaymentAggregate, Payment> =
        eventSourcingServiceFactory.create()
}