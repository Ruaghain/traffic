package com.assessment.traffic.data.entity;

import com.assessment.traffic.data.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TimeOfDay extends BaseEntity {

  private DayPart timeOfDayValue;
  private int duration;


  @Column
  public DayPart getTimeOfDayValue() {
    return timeOfDayValue;
  }

  public void setTimeOfDayValue(DayPart timeOfDayValue) {
    this.timeOfDayValue = timeOfDayValue;
  }

  @Column
  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }
}
