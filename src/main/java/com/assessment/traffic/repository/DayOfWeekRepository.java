package com.assessment.traffic.repository;

import com.assessment.traffic.data.entity.DayOfWeek;
import org.springframework.data.repository.Repository;

public interface DayOfWeekRepository extends Repository<DayOfWeek, Long> {

  DayOfWeek findByValue(int value);
}
