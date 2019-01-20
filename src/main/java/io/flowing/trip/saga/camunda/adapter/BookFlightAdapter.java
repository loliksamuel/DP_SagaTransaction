package io.flowing.trip.saga.camunda.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;

public class BookFlightAdapter implements JavaDelegate {
    @Value("${shouldCrashFlight:true}")
    private boolean shouldFail = true;

  @Override
  public void execute(DelegateExecution execution) throws Exception {


     
     if (shouldFail) {
         System.out.println("book flight crashes..  shouldCrashFlight="+shouldFail);
         throw new RuntimeException("Flight booking did not work");
     }
     else
         System.out.println("book flight succeeded");
    
  }



}
