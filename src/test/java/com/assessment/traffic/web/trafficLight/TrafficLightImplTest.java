package com.assessment.traffic.web.trafficLight;

import com.assessment.traffic.exception.TrafficException;
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
    Mockito.when(schedule.duration()).thenReturn(0);

    subject = new TrafficLightImpl(logger, schedule);
    subject.setChangeLightsOnce(true);
  }

  @Test
  public void givenTrafficLight_whenInitialised_willHaveStatusOffline() {
    Assert.assertEquals(TrafficLightStatus.OFFLINE, subject.status());
  }

  @Test
  public void givenTrafficLight_whenInitialised_willDisplayGreenLight() {
    Assert.assertEquals(LightColour.GREEN, subject.getCurrentlyDisplayed().getColour());
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

  @Test
  public void givenTrafficLight_whenChangingLights_willSetLightAppropriately() {
    subject.start();
    subject.changeLights();

    Mockito.verify(schedule, Mockito.atMost(3)).duration();
  }

  @Test
  public void givenTrafficLight_whenExecutingRun_willExecuteChangeLights() {
    TrafficLightImpl spiedTrafficLight = Mockito.spy(subject);
    spiedTrafficLight.run();
    Mockito.verify(spiedTrafficLight, Mockito.atLeastOnce()).changeLights();
  }

  @Test(expected = TrafficException.class)
  public void givenTrafficLight_whenChangingLights_willThrowAnExceptionForSchedule() {
    Mockito.when(schedule.duration()).thenThrow(RuntimeException.class);

    subject.start();
    subject.changeLights();
  }
}