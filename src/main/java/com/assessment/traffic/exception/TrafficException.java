package com.assessment.traffic.exception;

/**
 * Add in an unchecked exception so I'm not forced to handle it all over the place should I use
 * it in several locations. Handle exceptions in the service layer, and then return a decent message
 * to the controller.
 */
public class TrafficException extends RuntimeException {

  public TrafficException(String message) {
    super(message);
  }
}
