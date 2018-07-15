package com.assessment.traffic.service;

import com.assessment.traffic.exception.TrafficException;
import com.assessment.traffic.web.trafficLight.TrafficLight;
import com.assessment.traffic.web.trafficLight.TrafficLightStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TrafficManagerServiceImpl implements TrafficManagerService {

  private static Logger logger;

  private List<TrafficLight> trafficLights;
  private TaskExecutor taskExecutor;

  @Autowired
  public TrafficManagerServiceImpl(Logger logger, List<TrafficLight> trafficLights, TaskExecutor taskExecutor) {
    TrafficManagerServiceImpl.logger = logger;
    this.trafficLights = trafficLights;
    this.taskExecutor = taskExecutor;
  }

  public List<TrafficLight> getTrafficLights() {
    return trafficLights;
  }

  //Added this post construct annotation here to start up the traffic light processing as soon as this bean
  //has been initialised.
  @PostConstruct
  @Override
  public boolean start() {
    logger.debug("STARTING - registered traffic lights.");
    try {
      if (CollectionUtils.isEmpty(trafficLights)) {
        throw new TrafficException("There are no traffic lights to process.");
      }

      for (TrafficLight trafficLight : trafficLights) {
        if (trafficLight.status() == TrafficLightStatus.OFFLINE) {
          trafficLight.start();
          //Start the traffic light in a new Thread so it doesn't hold up the web app.
          taskExecutor.execute(trafficLight);
        } else {
          logger.info("Traffic light is already online");
        }
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
    logger.debug("STOPPING - registered traffic lights.");
    try {
      if (CollectionUtils.isEmpty(trafficLights)) {
        throw new TrafficException("There are no traffic lights to process.");
      }

      for (TrafficLight trafficLight : trafficLights) {
        if (trafficLight.status() == TrafficLightStatus.ONLINE) {
          trafficLight.stop();
        } else {
          logger.info("Traffic light is already offline");
        }
      }
      logger.debug("Successfully stopped traffic lights");
    } catch (Exception e) {
      logger.error("There was an error stopping the traffic lights.", e);
      return false;
    }
    return true;
  }
}
