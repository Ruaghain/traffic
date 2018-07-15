package com.assessment.traffic.web.schedule;

import com.assessment.traffic.data.entity.DayOfWeek;
import com.assessment.traffic.data.entity.DayPart;
import com.assessment.traffic.data.entity.TimeOfDay;
import com.assessment.traffic.repository.DayOfWeekRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class DatabaseSchedule extends BaseSchedule {

  private static Logger logger;

  private DayOfWeekRepository dayOfWeekRepository;
  private Calendar calendar;

  @Autowired
  public DatabaseSchedule(Logger logger, DayOfWeekRepository dayOfWeekRepository) {
    DatabaseSchedule.logger = logger;
    this.dayOfWeekRepository = dayOfWeekRepository;
    this.calendar = Calendar.getInstance();
  }

  public void setCalendar(Calendar calendar) {
    this.calendar = calendar;
  }

  @Override
  public int duration() {
    logger.debug("Reading the duration from the database.");
    //Set 2 seconds as the default
    int result = getDurationInMilliseconds(DEFAULT_DURATION);
    int dayOfWeekValue = calendar.get(Calendar.DAY_OF_WEEK);
    DayOfWeek dayOfWeek = dayOfWeekRepository.findByValue(dayOfWeekValue);

    int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    DayPart period = getPartOfDay(timeOfDay);
    //TODO: Need to try and get the proper week day for the log message.
    logger.debug(String.format("Getting the duration for day '%s' in the '%s' period", dayOfWeek.getValue(), period));

    for (TimeOfDay time : dayOfWeek.getTimesOfDay()) {
      if (time.getTimeOfDayValue() == period) {
        result = getDurationInMilliseconds(time.getDuration());
      }
    }
    return result;
  }

  /**
   * This method returns the current part of the day (MORNING, AFTERNOON, EVENING, NIGHT)
   * depending on the hour.
   *
   * @param hourOfDay The hour of the day which you're on.
   * @return The specific part of the day, based on the time passed in.
   */
  private DayPart getPartOfDay(int hourOfDay) {
    DayPart dayPart = DayPart.UNKNOWN;
    if (hourOfDay >= 1 && hourOfDay < 12) {
      dayPart = DayPart.MORNING;
    } else if (hourOfDay >= 12 && hourOfDay < 17) {
      dayPart = DayPart.AFTERNOON;
    } else if (hourOfDay >= 17 && hourOfDay < 20) {
      dayPart = DayPart.EVENING;
    } else if (hourOfDay >= 20 && hourOfDay < 24) {
      dayPart = DayPart.NIGHT;
    }
    return dayPart;
  }
}
