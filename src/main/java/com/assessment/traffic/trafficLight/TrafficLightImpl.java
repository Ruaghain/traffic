package com.assessment.traffic.trafficLight;

import com.assessment.traffic.schedule.Schedule;
import com.assessment.traffic.trafficLight.light.Light;
import com.assessment.traffic.trafficLight.light.LightColour;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TrafficLightImpl implements TrafficLight {

  private static Logger logger;

//  private Thread thread;
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

//    thread = new Thread(this, "Traffic Light Thread");

    //Create the relevant lights for this traffic light.
    red = new Light(LightColour.RED);
    orange = new Light(LightColour.ORANGE);
    green = new Light(LightColour.GREEN);

    lights.add(red);
    lights.add(orange);
    lights.add(green);

    //Set the currently displayed traffic light.
    this.currentlyDisplayed = red;

    //Set the traffic light to offline initially.
    this.status = TrafficLightStatus.OFFLINE;
  }

  /**
   * This method returns the currently displayed light.
   *
   * @return The currently displayed light.
   */
  public Light getCurrentlyDisplayed() {
    return currentlyDisplayed;
  }

  @Override
  public void start() {
    logger.debug("Started Traffic Light");
    this.status = TrafficLightStatus.ONLINE;
//    //Start the traffic light processing in another thread so it doesn't stop the application.
//    thread.start();
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

  /**
   * This method searches the array list for the position of the currently selected light.
   *
   * @return The index of the currently selected light.
   */
  private int getIndexOfCurrentlyDisplayedLight() {
    return ListUtils.indexOf(lights, new Predicate<Light>() {
      @Override
      public boolean evaluate(Light light) {
        return light.getColour().equals(currentlyDisplayed.getColour());
      }
    });
  }

  @Override
  public void changeLights() {
    int currentIndex = getIndexOfCurrentlyDisplayedLight();
    //TODO: Look into implementing a circular queue as a replacement for this for loop if there's time
    //Starting from the end of the array because lights in Ireland go from Green --> Orange --> Red.
    //Could have just added them like that in the array, but it wouldn't be intuitive.
    for (int index = currentIndex; index >= -1; index--) {
      //Check the status of the robot, if it's off then bail.
      if (this.status == TrafficLightStatus.OFFLINE) {
        break;
      }
      //Reset the loop once the value reaches 0.
      if (getIndexOfCurrentlyDisplayedLight() == 0) {
        index = lights.size() - 1;
      }
      //Set the currently displayed light.
      this.currentlyDisplayed = lights.get(index);
      logger.debug(lights.get(index).getColour());
      try {
        Thread.sleep(schedule.duration());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void run() {
//    this.changeLights();
  }
}
