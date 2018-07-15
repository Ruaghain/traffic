package com.assessment.traffic.service;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class TrafficManagerServiceImplNegativeTest {

  private Logger logger;

  private TaskExecutor taskExecutor;
  private TrafficManagerService subject;

  @Before
  public void setUp() throws Exception {
    logger = mock(Logger.class);
    taskExecutor = mock(TaskExecutor.class);

    subject = new TrafficManagerServiceImpl(logger, null, taskExecutor);
  }

  @Test
  public void givenNoTrafficLights_whenGettingTheList_willReturnNull() {
    Mockito.verify(taskExecutor, Mockito.never()).execute(Mockito.any(Runnable.class));
    Assert.assertNull(subject.getTrafficLights());
  }

  @Test
  public void givenNoTrafficLights_whenStarting_willReturnFalse() {
    Mockito.verify(taskExecutor, Mockito.never()).execute(Mockito.any(Runnable.class));
    Assert.assertFalse(subject.start());
  }

  @Test
  public void givenNoTrafficLights_whenStopping_willReturnFalse() {
    Mockito.verify(taskExecutor, Mockito.never()).execute(Mockito.any(Runnable.class));
    Assert.assertFalse(subject.stop());
  }
}