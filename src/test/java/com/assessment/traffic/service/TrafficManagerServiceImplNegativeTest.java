package com.assessment.traffic.service;

import com.assessment.traffic.exception.TrafficException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class TrafficManagerServiceImplNegativeTest {

  private Logger logger;
  private TrafficManagerService subject;

  @Before
  public void setUp() throws Exception {
    logger = mock(Logger.class);
    subject = new TrafficManagerServiceImpl(logger, null);
  }

  @Test
  public void givenNoTrafficLights_whenGettingTheList_willReturnNull() {
    Assert.assertNull(subject.getTrafficLights());
  }

  @Test
  public void givenNoTrafficLights_whenStarting_willReturnFalse() {
    Assert.assertFalse(subject.start());
  }

  @Test
  public void givenNoTrafficLights_whenStopping_willReturnFalse() {
    Assert.assertFalse(subject.stop());
  }
}