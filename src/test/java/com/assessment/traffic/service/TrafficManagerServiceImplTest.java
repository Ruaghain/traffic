package com.assessment.traffic.service;

import com.assessment.traffic.web.trafficLight.TrafficLight;
import com.assessment.traffic.web.trafficLight.TrafficLightStatus;
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
  private TrafficLight trafficLight1;
  private TrafficLight trafficLight2;

  private TrafficManagerService subject;

  @Before
  public void setUp() throws Exception {
    logger = mock(Logger.class);

    trafficLight1 = mock(TrafficLight.class);
    trafficLight2 = mock(TrafficLight.class);

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
    Mockito.when(trafficLight1.status()).thenReturn(TrafficLightStatus.OFFLINE);
    Mockito.when(trafficLight2.status()).thenReturn(TrafficLightStatus.OFFLINE);

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
    Mockito.when(trafficLight1.status()).thenReturn(TrafficLightStatus.ONLINE);
    Mockito.when(trafficLight2.status()).thenReturn(TrafficLightStatus.ONLINE);

    boolean result = subject.stop();
    List<TrafficLight> trafficLights = subject.getTrafficLights();
    for (TrafficLight trafficLight : trafficLights) {
      verify(trafficLight, Mockito.times(1)).stop();
    }
    Assert.assertTrue(result);
  }

  @Test
  public void givenMultipleTrafficLights_whenStoppingTrafficLightsWhichAreAlreadyStopped_willCallLog() {
    Mockito.when(trafficLight1.status()).thenReturn(TrafficLightStatus.OFFLINE);
    Mockito.when(trafficLight2.status()).thenReturn(TrafficLightStatus.OFFLINE);

    boolean result = subject.stop();
    verify(logger, Mockito.times(2)).info(Mockito.any());
    Assert.assertTrue(result);
  }

  @Test
  public void givenMultipleTrafficLights_whenStartingTrafficLightsWhichAreAlreadyStarted_willCallLog() {
    Mockito.when(trafficLight1.status()).thenReturn(TrafficLightStatus.ONLINE);
    Mockito.when(trafficLight2.status()).thenReturn(TrafficLightStatus.ONLINE);

    boolean result = subject.start();
    verify(logger, Mockito.times(2)).info(Mockito.any());
    Assert.assertTrue(result);
  }
}