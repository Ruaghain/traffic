package com.assessment.traffic.data.entity;

import com.assessment.traffic.data.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DayOfWeek extends BaseEntity {

  private int value;
  private Set<TimeOfDay> timesOfDay = new HashSet<>();

  public DayOfWeek() {

  }

  @Column(name = "day_of_week_value")
  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "day_of_week_time_of_day_id")
  public Set<TimeOfDay> getTimesOfDay() {
    return timesOfDay;
  }

  public void setTimesOfDay(Set<TimeOfDay> timesOfDay) {
    this.timesOfDay = timesOfDay;
  }
}
