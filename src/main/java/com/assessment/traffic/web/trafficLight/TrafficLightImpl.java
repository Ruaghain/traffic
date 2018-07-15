package com.assessment.traffic.web.trafficLight;

import com.assessment.traffic.web.schedule.Schedule;
import com.assessment.traffic.web.trafficLight.light.Light;
import com.assessment.traffic.web.trafficLight.light.LightColour;
import org.apache.commons.collections4.ListUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Scope("prototype")
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
  public TrafficLightImpl(Logger logger, @Qualifier("databaseSchedule") Schedule schedule) {
    TrafficLightImpl.logger = logger;
    this.schedule = schedule;

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

  private void setCurrentlyDisplayed(Light currentlyDisplayed) {
      this.currentlyDisplayed = currentlyDisplayed;
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
  private int getCurrentlyDisplayedIndex() {
    return ListUtils.indexOf(lights, (light) -> light.getColour().equals(currentlyDisplayed.getColour()));
  }

  @Override
  public void changeLights() {
    int currentIndex = getCurrentlyDisplayedIndex();
    //TODO: Look into implementing a circular queue as a replacement for this for loop if there's time
    //Starting from the end of the array because lights in Ireland go from Green --> Orange --> Red.
    //Could have just added them like that in the array, but it wouldn't be intuitive.
    for (int index = currentIndex; index >= -1; index--) {
      //Check the status of the robot, if it's off then bail.
      if (this.status == TrafficLightStatus.OFFLINE) {
        break;
      }

      //Reset the loop once the value reaches 0.
      if (getCurrentlyDisplayedIndex() == 0) {
        index = lights.size() - 1;
      }
      //Set the currently displayed light.
      setCurrentlyDisplayed(lights.get(index));
      logger.debug(String.format("Currently displaying '%s'.", getCurrentlyDisplayed().getColour()));
      try {
        int duration = schedule.duration();
        logger.debug(String.format("Interval is currently '%d' seconds.", duration / 1000));
        Thread.sleep(duration);
      } catch (InterruptedException e) {
        logger.error("There was an error during the sleep process.", e);
      }
    }
  }

  @Override
  public void run() {
    this.changeLights();
  }
}
