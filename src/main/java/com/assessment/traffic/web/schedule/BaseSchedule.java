package com.assessment.traffic.web.schedule;

public abstract class BaseSchedule implements Schedule {

  protected static final int DEFAULT_DURATION = 2;

  protected int getDurationInMilliseconds(int duration) {
    return duration * 1000;
  }

  @Override
  public abstract int duration();
}
