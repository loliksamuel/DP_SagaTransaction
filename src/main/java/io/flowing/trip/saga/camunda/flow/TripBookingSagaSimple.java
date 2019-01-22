package io.flowing.trip.saga.camunda.flow;

import io.flowing.trip.saga.camunda.adapter.*;
import io.flowing.trip.saga.camunda.springboot.builder.SagaBuilder;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
//@Singleton
public class TripBookingSagaSimple {

  @Autowired
  private ProcessEngine   camunda;

//  @Autowired
//  private RuntimeService runtimeService;

  @Value("${succeed.flight:true}")
  boolean shouldSucceed;

  @Value("${succeed.car:true}")
  String shouldSucceedCar;

  @PostConstruct
  public void defineSaga() {

    // Configure and startup (in memory) engine
   // ProcessEngine camunda = new StandaloneInMemProcessEngineConfiguration().buildProcessEngine();

    System.out.println("shouldSucceedCar="+shouldSucceedCar+" shouldSucceed="+shouldSucceed);
    SagaBuilder saga = SagaBuilder.newSaga("trip") //
        .activity            ("Book    car"  , BookCarAdapter.class) //
        .compensationActivity("Cancel  car"  , CancelCarAdapter.class) //
        .activity            ("Book   hotel" , BookHotelAdapter.class) //
        .compensationActivity("Cancel hotel" , CancelHotelAdapter.class) //
        .activity            ("Book   flight", BookFlightAdapter.class) //
        .compensationActivity("Cancel flight", CancelFlightAdapter.class) //
        .end() //
        .triggerCompensationOnAnyError();//flow.eventSubProcess()...

    // get model instance
    BpmnModelInstance modelInstance = saga.getModel();

    // write to file to be able to open it in Camunda Modeler
    Bpmn.writeModelToFile(new File("result.bpmn"), modelInstance);

    //deploy
    camunda.getRepositoryService().createDeployment() //
        .addModelInstance("trip.bpmn", modelInstance) //
        .deploy();
/*

    // run instances of our saga - its state will be persisted
    camunda.getRuntimeService().startProcessInstanceByKey(
            "trip",
            Variables.putValue("someVariableToPass", "someValue")
                     .putValue("name", "mazda"));
*/







  }

}
