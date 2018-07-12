package com.assessment.traffic.service;

import com.assessment.traffic.trafficLight.TrafficLight;
import com.assessment.traffic.trafficLight.TrafficLightImpl;
import org.apache.catalina.core.ApplicationContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrafficManagerServiceImpl implements TrafficManagerService {

  private static Logger logger;
  private List<TrafficLight> trafficLights;

  @Autowired
  public TrafficManagerServiceImpl(Logger logger, List<TrafficLight> trafficLights) {
    TrafficManagerServiceImpl.logger = logger;
    this.trafficLights = trafficLights;
  }

  //Added this post construct annotation here to start up the traffic light processing as soon as this bean
  //has been initialised.
  @PostConstruct
  @Override
  public void start() {
    logger.info("STARTING - Automatically starting registered traffic lights.");
    try {
      for (TrafficLight trafficLight : trafficLights) {
        trafficLight.start();
      }
    } catch (Exception e) {
      logger.error("There was a problem starting the traffic lights", e);
    }
  }

  @Override
  public void stop() {
    logger.info("STOPPING - Automatically stopping registered traffic lights.");
    try {
      for (TrafficLight trafficLight : trafficLights) {
        trafficLight.stop();
      }
    } catch (Exception e) {
      logger.error("There was a problem stopping the traffic lights", e);
    }
  }
}
