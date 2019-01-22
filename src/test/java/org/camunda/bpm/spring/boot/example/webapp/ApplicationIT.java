package org.camunda.bpm.spring.boot.example.webapp;


import io.flowing.trip.saga.camunda.App;
import org.camunda.bpm.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { App.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {

  @Autowired
  private RuntimeService runtimeService;
  /*
    @Autowired
    private TaskService taskService;

    @Autowired
    @Rule
    public ProcessEngineRule processEngineRule;

    @Test
    @Deployment
    public void simpleProcessTest() {
      runtimeService.startProcessInstanceByKey("simpleProcess");
      Task task = taskService.createTaskQuery().singleResult();
      assertEquals("My Task", task.getName());

      taskService.complete(task.getId());
      assertEquals(0, runtimeService.createProcessInstanceQuery().count());

    }
    */
  @Test
  public void startUpTest() {
    // context init test
  }

}
