package ru.quipy.nested.service1.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.nested.service1.api.Service1Processed2Event
import ru.quipy.nested.service1.api.Service1Aggregate
import ru.quipy.nested.service1.logic.Service1
import ru.quipy.saga.SagaManager
import java.util.*

@RestController
class Service1Controller(
    val service1EsService: EventSourcingService<UUID, Service1Aggregate, Service1>,
    val sagaManager: SagaManager,
) {
    private val sagaName = "SAGA_EXAMPLE"

    @PostMapping("/start")
    fun startSaga(): Service1Processed2Event {
        val startSagaContext = sagaManager
            .launchSaga(sagaName, "start saga")
            .sagaContext
        val startEvent = service1EsService.create(startSagaContext) {
            it.startSaga(UUID.randomUUID())
        }

        val process1SagaContext = sagaManager
            .withContextGiven(startEvent.sagaContext)
            .performSagaStep(sagaName, "process1 service1")
            .sagaContext
        val process1Event = service1EsService.update(startEvent.service1Id, process1SagaContext) {
            it.process1Saga()
        }

        val process2SagaContext = sagaManager
            .withContextGiven(process1Event.sagaContext)
            .performSagaStep(sagaName, "process2 service1")
            .sagaContext
        return service1EsService.update(process1Event.service1Id, process2SagaContext) {
            it.process2Saga()
        }
    }
}