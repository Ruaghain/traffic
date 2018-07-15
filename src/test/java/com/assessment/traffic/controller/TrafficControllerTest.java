package com.assessment.traffic.controller;

import com.assessment.traffic.service.TrafficManagerService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TrafficControllerTest {

  private Logger logger;
  private TrafficManagerService trafficManagerService;
  private TrafficController subject;

  @Before
  public void setUp() {
    logger = mock(Logger.class);
    trafficManagerService = mock(TrafficManagerService.class);
    subject = new TrafficController(logger, trafficManagerService);
  }

  @Test
  public void givenTrafficController_whenStopTrafficLightsIsCalled_willReturnASuccessMessage() {
    Mockito.when(trafficManagerService.stop()).thenReturn(true);

    String result = subject.stopTrafficLights();

    Mockito.verify(trafficManagerService, Mockito.only()).stop();
    Assert.assertEquals("Successfully stopped the traffic lights", result);
  }

  @Test
  public void givenTrafficController_whenStopTrafficLightsIsCalled_willReturnAnErrorMessage() {
    Mockito.when(trafficManagerService.stop()).thenReturn(false);

    String result = subject.stopTrafficLights();

    Mockito.verify(trafficManagerService, Mockito.only()).stop();
    Assert.assertEquals("ERROR - There was a problem stopping the traffic lights", result);
  }

  @Test
  public void givenTrafficController_whenStartTrafficLightsIsCalled_willReturnASuccessMessage() {
    Mockito.when(trafficManagerService.start()).thenReturn(true);

    String result = subject.startTrafficLights();

    Mockito.verify(trafficManagerService, Mockito.only()).start();
    Assert.assertEquals("Successfully started the traffic lights", result);
  }

  @Test
  public void givenTrafficController_whenStartTrafficLightsIsCalled_willReturnAnErrorMessage() {
    Mockito.when(trafficManagerService.start()).thenReturn(false);

    String result = subject.startTrafficLights();

    Mockito.verify(trafficManagerService, Mockito.only()).start();
    Assert.assertEquals("ERROR - There was a problem starting the traffic lights", result);
  }

}