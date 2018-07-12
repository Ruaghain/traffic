package com.assessment.traffic.schedule;

import org.springframework.stereotype.Component;

@Component
public class FixedScheduleImpl implements Schedule {

  /**
   * This method returns the duration of the schedule
   *
   * @return 2000 - the duration for fixed schedules.
   */
  @Override
  public int duration() {
    return 2 * 1000;
  }
}
