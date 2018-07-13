package com.assessment.traffic.trafficLight;

public interface TrafficLight extends Runnable {
  /**
   * This method starts the traffic light process
   */
  void start();

  /**
   * This method stops the traffic lights.
   */
  void stop();

  /**
   * This method returns the status of this traffic light
   *
   * @return The traffic light status.
   */
  TrafficLightStatus status();

  /**
   * This method changes the lights of the traffic light.
   */
  void changeLights();
}
