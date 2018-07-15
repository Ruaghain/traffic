package com.assessment.traffic.service;

import com.assessment.traffic.web.trafficLight.TrafficLight;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TrafficManagerServiceImplTest {

  private Logger logger;

  private List<TrafficLight> trafficLights = new ArrayList<>();
  private TaskExecutor taskExecutor;

  private TrafficManagerService subject;

  @Before
  public void setUp() throws Exception {
    logger = mock(Logger.class);

    TrafficLight trafficLight1 = mock(TrafficLight.class);
    TrafficLight trafficLight2 = mock(TrafficLight.class);
    taskExecutor = mock(TaskExecutor.class);

    trafficLights.add(trafficLight1);
    trafficLights.add(trafficLight2);

    subject = new TrafficManagerServiceImpl(logger, trafficLights, taskExecutor);
  }

  @Test
  public void givenMultipleTrafficLights_whenGettingTheList_willReturnTheCorrectNumber() {
    Assert.assertEquals(2, subject.getTrafficLights().size());
  }

  @Test
  public void givenMultipleTrafficLights_whenStartingTheTrafficLights_willCallStart() {
    boolean result = subject.start();
    List<TrafficLight> trafficLights = subject.getTrafficLights();
    for (TrafficLight trafficLight : trafficLights) {
      verify(trafficLight, Mockito.times(1)).start();
    }
    verify(taskExecutor, Mockito.times(2)).execute(Mockito.any(Runnable.class));
    Assert.assertTrue(result);
  }

  @Test
  public void givenMultipleTrafficLights_whenStoppingTheTrafficLights_willCallStop() {
    boolean result = subject.stop();
    List<TrafficLight> trafficLights = subject.getTrafficLights();
    for (TrafficLight trafficLight : trafficLights) {
      verify(trafficLight, Mockito.times(1)).stop();
    }
    Assert.assertTrue(result);
  }
}