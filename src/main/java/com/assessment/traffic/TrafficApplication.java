package com.assessment.traffic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class TrafficApplication {

  @Bean
  @Scope("prototype")
    //This stops the requirement of having to add logging boiler plate code at the top of every class.
  Logger logger(InjectionPoint injectionPoint) {
    return Logger.getLogger(injectionPoint.getMethodParameter().getContainingClass());
  }

  public static void main(String[] args) {
    SpringApplication.run(TrafficApplication.class, args);
  }
}
