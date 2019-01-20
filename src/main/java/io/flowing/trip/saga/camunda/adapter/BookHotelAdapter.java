package io.flowing.trip.saga.camunda.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class BookHotelAdapter implements JavaDelegate {

  private boolean shouldSucceed = false;

  @Override
  public void execute(DelegateExecution execution) throws Exception {



    if (shouldSucceed) {
      System.out.println("book hotel succeeded");

    }
    else{
      System.out.println("book hotel crashes..  shouldSucceed=" + shouldSucceed);
      throw new RuntimeException("hotel booking did not work");
    }

  }

}
