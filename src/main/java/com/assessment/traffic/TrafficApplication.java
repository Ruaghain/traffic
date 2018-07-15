package com.assessment.traffic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class TrafficApplication {

  @Bean
  @Scope("prototype")
    //This stops the requirement of having to add logging boiler plate code at the top of every class.
  Logger logger(InjectionPoint injectionPoint) {
    return Logger.getLogger(injectionPoint.getMethodParameter().getContainingClass());
  }

  @Bean
  public TaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(4);
    executor.setMaxPoolSize(4);
    executor.setThreadNamePrefix("TrafficService-");
    executor.initialize();
    return executor;
  }

  public static void main(String[] args) {
    SpringApplication.run(TrafficApplication.class, args);
  }
}
