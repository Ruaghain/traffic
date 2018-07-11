package com.assessment.traffic.trafficLights;

import com.assessment.traffic.trafficLights.light.Light;
import com.assessment.traffic.trafficLights.light.LightColour;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TrafficManagerImpl implements TrafficManager {

    private static Logger logger;

    private static int TRANSITION_PERIOD = 2;

    ArrayList<Light> lights = new ArrayList<>();
    private Light red;
    private Light orange;
    private Light green;

    //TODO: Inject a scheduler into this class. It will determine the time between light changes.
    @Autowired
    public TrafficManagerImpl(Logger logger) {
        TrafficManagerImpl.logger = logger;
        red = new Light(LightColour.RED, false);
        orange = new Light(LightColour.ORANGE, false);
        green = new Light(LightColour.GREEN, false);

        lights.add(red);
        lights.add(green);
        lights.add(orange);
    }

    @Override
    public void start() {
        logger.debug("Started the Traffic Light Manager");
        int interval = TRANSITION_PERIOD * 1000;
    }

    @Override
    public void changeLights() {

    }
}
