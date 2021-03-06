package io.flowing.trip.saga.camunda.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class BookFlightAdapter implements JavaDelegate {

  private boolean shouldSucceed = true;


  @Override
  public void execute(DelegateExecution execution) throws Exception {


     if (shouldSucceed) {
         System.out.println("book flight succeeded");

     }
     else{
         System.out.println("book flight crashes..  fail.flight.enabled=" + shouldSucceed);
         throw new RuntimeException("Flight booking did not work");
     }

    
  }

}
