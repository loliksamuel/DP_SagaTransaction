package io.flowing.trip.saga.camunda.flow;

import io.flowing.trip.saga.camunda.adapter.*;
import io.flowing.trip.saga.camunda.springboot.builder.SagaBuilder;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
//@Singleton
public class TripBookingSagaSimple {

  @Autowired
  private ProcessEngine camunda;

  @PostConstruct
  public void defineSaga() {
    SagaBuilder saga = SagaBuilder.newSaga("trip") //
        .activity            ("Book    car"  , BookCarAdapter.class) //
        .compensationActivity("Cancel  car"  , CancelCarAdapter.class) //
        .activity            ("Book   hotel" , BookHotelAdapter.class) //
        .compensationActivity("Cancel hotel" , CancelHotelAdapter.class) //
        .activity            ("Book   flight", BookFlightAdapter.class) //
        .compensationActivity("Cancel flight", CancelFlightAdapter.class) //
        .end() //
        .triggerCompensationOnAnyError();

    // optional: Write to file to be able to open it in Camunda Modeler
    //Bpmn.writeModelToFile(new File("result.bpmn"), saga.getModel());

    camunda.getRepositoryService().createDeployment() //
        .addModelInstance("trip.bpmn", saga.getModel()) //
        .deploy();








  }

}
