package com.assessment.traffic.service;

import com.assessment.traffic.web.trafficLight.TrafficLight;

import java.util.List;

public interface TrafficManagerService {
  boolean start();
  boolean stop();
  List<TrafficLight> getTrafficLights();
}
