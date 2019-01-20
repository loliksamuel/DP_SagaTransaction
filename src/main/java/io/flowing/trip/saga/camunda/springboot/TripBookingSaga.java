package io.flowing.trip.saga.camunda.springboot;

import javax.annotation.PostConstruct;

import io.flowing.trip.saga.camunda.adapter.*;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.flowing.trip.saga.camunda.adapter.BookCarAdapter;
import io.flowing.trip.saga.camunda.springboot.builder.SagaBuilder;

@Component
//@Singleton
public class TripBookingSaga {

  @Autowired
  private ProcessEngine camunda;

  @PostConstruct
  public void defineSaga() {
    SagaBuilder saga = SagaBuilder.newSaga("trip") //
        .activity            ("Book    car", BookCarAdapter.class) //
        .compensationActivity("Cancel  car", CancelCarAdapter.class) //
        .activity            ("Book   hotel", BookHotelAdapter.class) //
        .compensationActivity("Cancel hotel", CancelHotelAdapter.class) //
        .activity            ("Book   flight", BookFlightAdapter.class) //
        .compensationActivity("Cancel flight", CancelFlightAdapter.class) //
        .end() //
        .triggerCompensationOnAnyError();

    camunda.getRepositoryService().createDeployment() //
        .addModelInstance("trip.bpmn", saga.getModel()) //
        .deploy();

//    File file = new File("result.bpmn");
//    Bpmn.writeModelToFile(file, saga.getModel());
  }

}
