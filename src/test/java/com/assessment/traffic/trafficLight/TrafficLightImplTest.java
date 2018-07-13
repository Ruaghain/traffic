package com.assessment.traffic.trafficLight;

import com.assessment.traffic.schedule.Schedule;
import com.assessment.traffic.trafficLight.light.LightColour;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrafficLightImplTest {

  private Logger logger;
  private Schedule schedule;
  private TrafficLightImpl trafficLight;

  @Before
  public void setUp() throws Exception {
    logger = mock(Logger.class);
    schedule = mock(Schedule.class);
    Mockito.when(schedule.duration()).thenReturn(2000);

    trafficLight = new TrafficLightImpl(logger, schedule);
  }

  @Test
  public void givenTrafficLight_whenInitialised_willHaveStatusOffline() {
    Assert.assertEquals(TrafficLightStatus.OFFLINE, trafficLight.status());
  }

  @Test
  public void givenTrafficLight_whenInitialised_willDisplayRedLight() {
    Assert.assertEquals(LightColour.RED, trafficLight.getCurrentlyDisplayed().getColour());
  }

  @Test
  public void givenTrafficLight_whenStarting_willSetStatusToOnline() {
    trafficLight.start();
    Assert.assertEquals(TrafficLightStatus.ONLINE, trafficLight.status());
  }

  @Test
  public void givenTrafficLight_whenStopping_willSetStatusToOffline() {
    trafficLight.stop();
    Assert.assertEquals(TrafficLightStatus.OFFLINE, trafficLight.status());
  }

 // @Test
  public void givenTrafficLight_whenChangingLights_willSetLightAppropriately() throws InterruptedException {
    trafficLight.start();
    trafficLight.changeLights();
    trafficLight.stop();
  }
}