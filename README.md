# Traffic Controller

This application simulates the lights switching in a traffic light with a predefined delay between each. 
This duration value is read from a database, and is determined by the day of the week, along with the period 
within the day, namely:

* MORNING
* AFTERNOON
* EVENING
* NIGHT

To start the application please run the following command `gradle bootRun` - This obviously assumes that you have gradle installed.

Once the application has started, you'll notice that the relevant light transitioning will immediately be 
logged to the screen, along with the duration.

The user also has the ability to stop and start the processing of the traffic lights by navigating 
to the following URLs:

* [http://localhost:8080/traffic/stop](http://localhost:8080/traffic/stop)
* [http://localhost:8080/traffic/start](http://localhost:8080/traffic/start)

A message will be displayed to you confirming the successful execution of the requests 
(or an error if there was a failure)
  
