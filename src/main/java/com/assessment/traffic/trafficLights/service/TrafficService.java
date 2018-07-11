package com.assessment.traffic.trafficLights.service;

import com.assessment.traffic.trafficLights.TrafficManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TrafficService {

    private static Logger logger;
    private final TrafficManager trafficManager;

    @Autowired
    public TrafficService(Logger logger, TrafficManager trafficManager) {
        TrafficService.logger = logger;
        this.trafficManager = trafficManager;
    }

    //Added this post construct annotation here to start up the traffic light processing as soon as this bean
    //has been initialised.
    @PostConstruct
    public void init() {
        logger.info("STARTING - Automatically starting the traffic light manager");
        trafficManager.start();
    }
}
