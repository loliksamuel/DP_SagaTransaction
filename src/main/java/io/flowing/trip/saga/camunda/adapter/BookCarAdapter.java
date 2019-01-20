package io.flowing.trip.saga.camunda.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class BookCarAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution ctx) throws Exception {

    System.out.println("book car for '" + ctx.getVariable("name") +  "' succeeded");

  }

}
