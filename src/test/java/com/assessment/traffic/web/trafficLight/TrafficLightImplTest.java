package com.assessment.traffic.web.trafficLight;

import com.assessment.traffic.web.schedule.Schedule;
import com.assessment.traffic.web.trafficLight.light.LightColour;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class TrafficLightImplTest {

  private Logger logger;
  private Schedule schedule;
  private TrafficLightImpl subject;

  @Before
  public void setUp() throws Exception {
    logger = mock(Logger.class);
    schedule = mock(Schedule.class);
    Mockito.when(schedule.duration()).thenReturn(2000);

    subject = new TrafficLightImpl(logger, schedule);
  }

  @Test
  public void givenTrafficLight_whenInitialised_willHaveStatusOffline() {
    Assert.assertEquals(TrafficLightStatus.OFFLINE, subject.status());
  }

  @Test
  public void givenTrafficLight_whenInitialised_willDisplayRedLight() {
    Assert.assertEquals(LightColour.RED, subject.getCurrentlyDisplayed().getColour());
  }

  @Test
  public void givenTrafficLight_whenStarting_willSetStatusToOnline() {
    subject.start();
    Assert.assertEquals(TrafficLightStatus.ONLINE, subject.status());
  }

  @Test
  public void givenTrafficLight_whenStopping_willSetStatusToOffline() {
    subject.stop();
    Assert.assertEquals(TrafficLightStatus.OFFLINE, subject.status());
  }

  @Test
  public void givenTrafficLight_whenChangingLights_willBreakIfNotOnline() {
    subject.changeLights();
    Mockito.verify(schedule, Mockito.never()).duration();
  }

//  @Test
//  public void givenTrafficLight_whenChangingLights_willSetLightAppropriately() throws InterruptedException {
//    subject.changeLights();
//    TimeUnit.SECONDS.sleep(5);
//    subject.stop();
//
//    Mockito.verify(schedule, Mockito.atLeast(1)).duration();
//  }
}