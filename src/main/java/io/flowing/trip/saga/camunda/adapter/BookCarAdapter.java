package io.flowing.trip.saga.camunda.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;

public class BookCarAdapter implements JavaDelegate {

  @Value("${name}")
  private String name;

  @Override
  public void execute(DelegateExecution ctx) throws Exception {

    System.out.println("book car for '" + ctx.getVariable("name") +  "' succeeded");

  }

}
