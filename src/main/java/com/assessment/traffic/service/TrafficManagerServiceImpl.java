package com.assessment.traffic.service;

import com.assessment.traffic.exception.TrafficException;
import com.assessment.traffic.web.trafficLight.TrafficLight;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

  public List<TrafficLight> getTrafficLights() {
    return trafficLights;
  }

  //Added this post construct annotation here to start up the traffic light processing as soon as this bean
  //has been initialised.
//  @PostConstruct
  @Override
  public boolean start() {
    logger.debug("STARTING - Automatically starting registered traffic lights.");
    try {
      if (CollectionUtils.isEmpty(trafficLights)) {
        throw new TrafficException("There are no traffic lights to process.");
      }

      for (TrafficLight trafficLight : trafficLights) {
        trafficLight.start();
        trafficLight.changeLights();
      }
      logger.debug("Successfully started traffic lights");
    } catch (Exception e) {
      logger.error("There was an error starting the traffic lights.", e);
      return false;
    }
    return true;
  }

  @Override
  public boolean stop() {
    logger.debug("STOPPING - Automatically stopping registered traffic lights.");
    try {
      if (CollectionUtils.isEmpty(trafficLights)) {
        throw new TrafficException("There are no traffic lights to process.");
      }

      for (TrafficLight trafficLight : trafficLights) {
        trafficLight.stop();
      }
      logger.debug("Successfully stopped traffic lights");
    } catch (Exception e) {
      logger.error("There was an error stopping the traffic lights.", e);
      return false;
    }
    return true;
  }
}
