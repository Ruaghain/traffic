package com.assessment.traffic.web.schedule;

import org.springframework.stereotype.Component;

@Component
public class FixedSchedule implements Schedule {

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
