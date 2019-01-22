package io.flowing.trip.saga.camunda.flow;

import io.flowing.trip.saga.camunda.adapter.*;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;

import io.flowing.trip.saga.camunda.adapter.BookCarAdapter;

import java.io.File;

/**
 * One main class containing everything to run a Saga
 * using Camunda and BPMN.
 * The class runs an in memory engine,
 * defines the Saga and run a couple of instances.
 */
public class TripBookingSaga {

  public static void main(String[] args) {
            // Configure and startup (in memory) engine
            ProcessEngine camunda = new StandaloneInMemProcessEngineConfiguration().buildProcessEngine();

            // define saga as BPMN process
            ProcessBuilder flow = Bpmn.createExecutableProcess("trip");

            // build flow of activities and compensating actions
            flow.startEvent()
                .serviceTask("car")      .name("book car").camundaClass(BookCarAdapter.class)
                                                              .boundaryEvent().compensateEventDefinition().compensateEventDefinitionDone()
                                                              .compensationStart().serviceTask("CancelCar")
                                                              .camundaClass(CancelCarAdapter.class).compensationDone()
                .serviceTask("hotel")  .name("Book hotel").camundaClass(BookHotelAdapter.class)
                                                              .boundaryEvent().compensateEventDefinition().compensateEventDefinitionDone()
                                                              .compensationStart().serviceTask("CancelHotel")
                                                              .camundaClass(CancelHotelAdapter.class).compensationDone()
                .serviceTask("flight").name("Book flight").camundaClass(BookFlightAdapter.class)
                                                              .boundaryEvent().compensateEventDefinition().compensateEventDefinitionDone()
                                                              .compensationStart().serviceTask("CancelFlight")
                                                              .camundaClass(CancelFlightAdapter.class).compensationDone()
                .endEvent();

            // build compensation mechanism in case of any exception (other triggers are possible)
            flow.eventSubProcess()
                .startEvent().error("java.lang.Throwable")
                .intermediateThrowEvent().compensateEventDefinition().compensateEventDefinitionDone()
                .endEvent();







            // get model instance
            BpmnModelInstance modelInstance = flow.done();

            // write modelInstance to file to be able to open it in Camunda Modeler(optional)
            Bpmn.writeModelToFile(new File("trip.bpmn"), modelInstance);

            // deploy  model instance to Camunda
            camunda.getRepositoryService().createDeployment() //
                .addModelInstance("trip.bpmn", modelInstance) //
                .deploy();

            //run instances of our saga - its state will be persisted
            System.out.println("\nrun instance 1");
            System.out.println("================");
            camunda.getRuntimeService()
                   .startProcessInstanceByKey("trip", Variables.putValue("name", "trip1").putValue("name", "mazda"));

            System.out.println("\nrun instance 2");
            System.out.println("================");
            camunda.getRuntimeService()
                   .startProcessInstanceByKey("trip", Variables.putValue("name", "trip2").putValue("name", "mazda"));
  }

}
