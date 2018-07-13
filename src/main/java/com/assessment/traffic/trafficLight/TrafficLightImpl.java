package com.assessment.traffic.trafficLight;

import com.assessment.traffic.schedule.Schedule;
import com.assessment.traffic.trafficLight.light.Light;
import com.assessment.traffic.trafficLight.light.LightColour;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TrafficLightImpl implements TrafficLight {

  private static Logger logger;

  private Schedule schedule;
  private TrafficLightStatus status;
  private Light currentlyDisplayed;

  ArrayList<Light> lights = new ArrayList<>();
  private Light red;
  private Light orange;
  private Light green;

  @Autowired
  public TrafficLightImpl(Logger logger, Schedule schedule) {
    TrafficLightImpl.logger = logger;
    this.schedule = schedule;

    //Create the relevant lights for this traffic light.
    red = new Light(LightColour.RED);
    orange = new Light(LightColour.ORANGE);
    green = new Light(LightColour.GREEN);

    lights.add(red);
    lights.add(green);
    lights.add(orange);

    //Set the currently displayed traffic light.
    this.currentlyDisplayed = red;
  }

  /**
   * This method returns all the lights for this traffic light.
   *
   * @return The created lights.
   */
  public ArrayList<Light> getLights() {
    return lights;
  }

  @Override
  public void start() {
    logger.debug("Started Traffic Light");
    this.status = TrafficLightStatus.ONLINE;
  }

  @Override
  public void stop() {
    logger.debug("Stopped Traffic Light");
    this.status = TrafficLightStatus.OFFLINE;
  }

  @Override
  public TrafficLightStatus status() {
    return this.status;
  }

  @Override
  public void changeLights() {
    //Keep on changing the lights while the status of the traffic light is online.
//    while (this.status == TrafficLightStatus.ONLINE) {
//
//    }
  }
}
