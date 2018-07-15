package com.assessment.traffic.web.trafficLight;

import com.assessment.traffic.exception.TrafficException;
import com.assessment.traffic.web.schedule.Schedule;
import com.assessment.traffic.web.trafficLight.light.Light;
import com.assessment.traffic.web.trafficLight.light.LightColour;
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
  private ArrayList<Light> lights = new ArrayList<>();
  private boolean changeLightsOnce = false;

  @Autowired
  public TrafficLightImpl(Logger logger, @Qualifier("databaseSchedule") Schedule schedule) {
    TrafficLightImpl.logger = logger;
    this.schedule = schedule;

    //Create the relevant lights for this traffic light.
    Light red = new Light(LightColour.RED);
    Light orange = new Light(LightColour.ORANGE);
    Light green = new Light(LightColour.GREEN);

    lights.add(red);
    lights.add(orange);
    lights.add(green);

    //Set the currently displayed traffic light.
    this.currentlyDisplayed = green;

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

  public void setCurrentlyDisplayed(Light currentlyDisplayed) {
      this.currentlyDisplayed = currentlyDisplayed;
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

  public boolean isChangeLightsOnce() {
    return changeLightsOnce;
  }

  public void setChangeLightsOnce(boolean changeLightsOnce) {
    this.changeLightsOnce = changeLightsOnce;
  }


  @Override
  public void changeLights() {
    //Starting from the end of the array because lights in Ireland go from Green --> Orange --> Red.
    //Could have just added them like that in the array, but it wouldn't be intuitive.
    while (this.status == TrafficLightStatus.ONLINE) {
      for (int index = this.lights.size() - 1; index >= 0; index--) {
        //Set the currently displayed light.
        setCurrentlyDisplayed(lights.get(index));
        logger.debug(String.format("Currently displaying '%s'.", getCurrentlyDisplayed().getColour()));
        try {
          int duration = schedule.duration();
          Thread.sleep(duration);
          //Would generally not catch the exception here, but handle it in the service - you can't add
          //an exception to the method signature for the run method.
        } catch (Exception e) {
          logger.error("There was an error during the sleep process.", e);
          throw new TrafficException("There was an error during the sleep process");
        }
      }

      //This is for testing purposes, so it bails after only one execution.
      if (this.isChangeLightsOnce()) {
        break;
      }
    }
  }

  @Override
  public void run() {
    this.changeLights();
  }
}
