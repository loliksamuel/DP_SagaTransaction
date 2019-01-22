package io.flowing.trip.saga.camunda;


import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.sql.SQLException;

@SpringBootApplication
@EnableAutoConfiguration
@EnableProcessApplication
public class App {

  public static void main(String... args) throws Exception {
        SpringApplication.run(App.class, args);
        runCamunda();
        //runServer();
  }




  private static void runCamunda() {
        // do default setup of platform (everything is only applied if not yet there)
        ProcessEngine engine = BpmPlatform.getDefaultProcessEngine();

        // start a Saga right away
        engine.getRuntimeService().startProcessInstanceByKey(
            "trip",
            Variables.putValue("someVariableToPass", "someValue")
                     .putValue("name", "mazda"));
  }

  // Start H2 server to be able to connect to database from the outside
  private static void runServer() throws SQLException {
        Server.createTcpServer(new String[] { "-tcpPort", "8092", "-tcpAllowOthers" }).start();
  }

}
