package com.assessment.traffic.web.schedule;

import com.assessment.traffic.data.entity.DayOfWeek;
import com.assessment.traffic.data.entity.DayPart;
import com.assessment.traffic.data.entity.TimeOfDay;
import com.assessment.traffic.repository.DayOfWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class DatabaseSchedule implements Schedule {

  private DayOfWeekRepository dayOfWeekRepository;
  private Calendar calendar;

  @Autowired
  public DatabaseSchedule(DayOfWeekRepository dayOfWeekRepository) {
    this.dayOfWeekRepository = dayOfWeekRepository;
    this.calendar = Calendar.getInstance();
  }

  public void setCalendar(Calendar calendar) {
    this.calendar = calendar;
  }

  @Override
  public int duration() {
    int dayOfWeekValue = calendar.get(Calendar.DAY_OF_WEEK);
    DayOfWeek dayOfWeek = dayOfWeekRepository.findByValue(dayOfWeekValue);

    int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    DayPart period = getPartOfDay(timeOfDay);

    for (TimeOfDay time : dayOfWeek.getTimesOfDay()) {
      if (time.getTimeOfDayValue() == period) {
        return time.getDuration() * 1000;
      }
    }
    //Return 2 seconds as the default
    return 2000;
  }

  /**
   * This method returns the current part of the day (MORNING, AFTERNOON, EVENING, NIGHT)
   * depending on the hour.
   *
   * @param hourOfDay The hour of the day which you're on.
   * @return The specific part of the day, based on the time passed in.
   */
  private DayPart getPartOfDay(int hourOfDay) {
    DayPart dayPart = DayPart.MORNING;
    if (hourOfDay >= 12 && hourOfDay < 17) {
      dayPart = DayPart.AFTERNOON;
    } else if (hourOfDay >= 17 && hourOfDay < 20) {
      dayPart = DayPart.EVENING;
    } else if (hourOfDay >= 20 && hourOfDay < 24) {
      dayPart = DayPart.NIGHT;
    }
    return dayPart;
  }
}
