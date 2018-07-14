package com.assessment.traffic.web.schedule;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class FixedScheduleTest {

  private FixedSchedule subject;
  private Logger logger;

  @Before
  public void setUp() {
    logger = mock(Logger.class);
    subject = new FixedSchedule(logger);
  }

  @Test
  public void givenFixedSchedule_whenGettingDuration_willReturnTwoThousandMilliseconds() {
    int duration = subject.duration();
    Assert.assertEquals(2000, duration);
  }
}