package com.assessment.traffic.repository;

import com.assessment.traffic.data.entity.DayOfWeek;
import org.springframework.data.repository.Repository;

//Only need this repository as the related TIME_OF_DAY is eagerly loaded.
public interface DayOfWeekRepository extends Repository<DayOfWeek, Long> {

  DayOfWeek findByValue(int value);
}
