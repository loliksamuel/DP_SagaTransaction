package io.flowing.trip.saga.camunda.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class BookCarAdapter implements JavaDelegate {

  private boolean shouldSucceed = true;

  @Override
  public void execute(DelegateExecution execution) throws Exception {



    if (shouldSucceed) {
      System.out.println("book car for '" + execution.getVariable("name") +  "' succeeded");

    }
    else{
      System.out.println("book car crashes..  shouldSucceed=" + shouldSucceed);
      throw new RuntimeException("car booking did not work");
    }

  }

}
