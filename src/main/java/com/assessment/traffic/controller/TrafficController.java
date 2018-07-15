package com.assessment.traffic.controller;

import com.assessment.traffic.service.TrafficManagerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrafficController {

  private static Logger logger;

  private TrafficManagerService trafficManagerService;

  @Autowired
  public TrafficController(Logger logger, TrafficManagerService trafficManagerService) {
    TrafficController.logger = logger;
    this.trafficManagerService = trafficManagerService;
  }

  @RequestMapping("/traffic/stop")
  public String stopTrafficLights() {
    logger.info("Processing traffic 'stop' request.");
    if (this.trafficManagerService.stop()) {
      return "Successfully stopped the traffic lights";
    } else {
      return "ERROR - There was a problem stopping the traffic lights";
    }
  }

  @RequestMapping("/traffic/start")
  public String startTrafficLights() {
    logger.info("Processing traffic 'start' request.");
    if (this.trafficManagerService.start()) {
      return "Successfully started the traffic lights";
    } else {
      return "ERROR - There was a problem starting the traffic lights";
    }
  }
}
