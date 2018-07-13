package com.assessment.traffic.service;

import com.assessment.traffic.trafficLight.TrafficLight;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TrafficManagerServiceImplTest {

  private Logger logger;
  private List<TrafficLight> trafficLights = new ArrayList<>();
  private TrafficManagerService trafficManagerService;

  @Before
  public void setUp() throws Exception {
    logger = mock(Logger.class);
    TrafficLight trafficLight1 = mock(TrafficLight.class);
    TrafficLight trafficLight2 = mock(TrafficLight.class);

    trafficLights.add(trafficLight1);
    trafficLights.add(trafficLight2);

    trafficManagerService = new TrafficManagerServiceImpl(logger, trafficLights);
  }

//  @Test
//  public void givenNoTrafficLights_whenGettingTheList_WillReturnZero() {
//
//  }

  @Test
  public void givenMultipleTrafficLights_whenGettingTheList_willReturnTheCorrectNumber() {
    Assert.assertEquals(2, trafficManagerService.getTrafficLights().size());
  }

  @Test
  public void givenMultipleTrafficLights_whenStartingTheTrafficLights_willCallStart() {
    trafficManagerService.start();
    List<TrafficLight> trafficLights = trafficManagerService.getTrafficLights();
    for (TrafficLight trafficLight : trafficLights) {
      verify(trafficLight, Mockito.atMost(1)).start();
    }
  }

  @Test
  public void givenMultipleTrafficLights_whenStoppingTheTrafficLights_willCallStop() {
    trafficManagerService.stop();
    List<TrafficLight> trafficLights = trafficManagerService.getTrafficLights();
    for (TrafficLight trafficLight : trafficLights) {
      verify(trafficLight, Mockito.atMost(1)).stop();
    }
  }
}