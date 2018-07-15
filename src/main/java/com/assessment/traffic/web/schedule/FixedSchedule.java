package com.assessment.traffic.web.schedule;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FixedSchedule extends BaseSchedule {

  private static Logger logger;

  @Autowired
  public FixedSchedule(Logger logger) {
    FixedSchedule.logger = logger;
  }
  /**
   * This method returns the duration of the schedule
   *
   * @return 2000 - the duration for fixed schedules.
   */
  @Override
  public int duration() {
    logger.debug("Returning a fixed 2 milliseconds for the duration.");
    return getDurationInMilliseconds(DEFAULT_DURATION);
  }
}
