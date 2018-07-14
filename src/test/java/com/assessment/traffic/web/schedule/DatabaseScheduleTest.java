package com.assessment.traffic.web.schedule;

import com.assessment.traffic.data.entity.DayOfWeek;
import com.assessment.traffic.data.entity.DayPart;
import com.assessment.traffic.data.entity.TimeOfDay;
import com.assessment.traffic.repository.DayOfWeekRepository;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.mock;

public class DatabaseScheduleTest {

  private DayOfWeekRepository dayOfWeekRepository;
  private DatabaseSchedule subject;
  private Calendar calendar;
  private Logger logger;

  @Before
  public void setUp() throws Exception {
    dayOfWeekRepository = mock(DayOfWeekRepository.class);
    calendar = mock(Calendar.class);
    logger = mock(Logger.class);

    Mockito.when(dayOfWeekRepository.findByValue(Mockito.anyInt())).thenReturn(mockedDayOfWeek());
    subject = new DatabaseSchedule(logger, dayOfWeekRepository);
  }

  private DayOfWeek mockedDayOfWeek() {
    DayOfWeek dayOfWeek = new DayOfWeek();
    dayOfWeek.setValue(2);
    Set<TimeOfDay> timeOfDays = getTimeOfDays();
    dayOfWeek.setTimesOfDay(timeOfDays);

    return dayOfWeek;
  }

  private Set<TimeOfDay> getTimeOfDays() {
    Set<TimeOfDay> timeOfDays = new HashSet<>();

    TimeOfDay morning = new TimeOfDay();
    morning.setTimeOfDayValue(DayPart.MORNING);
    morning.setDuration(3);

    TimeOfDay afternoon = new TimeOfDay();
    afternoon.setTimeOfDayValue(DayPart.AFTERNOON);
    afternoon.setDuration(6);

    TimeOfDay evening = new TimeOfDay();
    evening.setTimeOfDayValue(DayPart.EVENING);
    evening.setDuration(9);

    TimeOfDay night = new TimeOfDay();
    night.setTimeOfDayValue(DayPart.NIGHT);
    night.setDuration(12);

    timeOfDays.add(morning);
    timeOfDays.add(afternoon);
    timeOfDays.add(evening);
    timeOfDays.add(night);

    return timeOfDays;
  }

  @Test
  public void givenDatabaseSchedule_whenGettingMondayMorning_willReturnCorrectDuration() {
    Mockito.when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(6);
    Mockito.when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(9);
    subject.setCalendar(calendar);

    int duration = subject.duration();
    Assert.assertEquals(3000, duration);
  }

  @Test
  public void givenDatabaseSchedule_whenGettingMondayAfternoon_willReturnCorrectDuration() {
    Mockito.when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(6);
    Mockito.when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(15);
    subject.setCalendar(calendar);

    int duration = subject.duration();
    Assert.assertEquals(6000, duration);
  }

  @Test
  public void givenDatabaseSchedule_whenGettingMondayEvening_willReturnCorrectDuration() {
    Mockito.when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(6);
    Mockito.when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(18);
    subject.setCalendar(calendar);

    int duration = subject.duration();
    Assert.assertEquals(9000, duration);
  }

  @Test
  public void givenDatabaseSchedule_whenGettingMondayNight_willReturnCorrectDuration() {
    Mockito.when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(6);
    Mockito.when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(22);
    subject.setCalendar(calendar);

    int duration = subject.duration();
    Assert.assertEquals(12000, duration);
  }

  @Test
  public void givenDatabaseSchedule_withInvalidCalendar_willReturnTwoThousandMilliseconds() {
    Mockito.when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(6);
    Mockito.when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(26);
    subject.setCalendar(calendar);

    int duration = subject.duration();
    Assert.assertEquals(2000, duration);
  }
}