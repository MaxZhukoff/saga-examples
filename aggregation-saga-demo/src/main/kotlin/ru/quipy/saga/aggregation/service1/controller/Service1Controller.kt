package ru.quipy.saga.aggregation.service1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import ru.quipy.saga.SagaManager
import ru.quipy.saga.aggregation.service1.api.Service1Aggregate
import ru.quipy.saga.aggregation.service1.api.Service1StartedEvent
import ru.quipy.saga.aggregation.service1.logic.Service1
import java.util.*

@RestController
class Service1Controller(
    val service1EsService: EventSourcingService<UUID, Service1Aggregate, Service1>,
    val sagaManager: SagaManager,
) {

    @PostMapping("/start")
    fun startSaga(): Service1StartedEvent {
        val sagaContext = sagaManager
            .launchSaga("SAGA_EXAMPLE", "start saga")
            .sagaContext

        return service1EsService.create(sagaContext) {
            it.startSaga(UUID.randomUUID())
        }
    }
}