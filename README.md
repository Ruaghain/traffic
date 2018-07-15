# Traffic Controller

This application simulates the lights switching in a traffic light with a predefined delay between each. 
This duration value is read from a database, and is determined by the day of the week, along with the period 
within the day, namely:

* MORNING
* AFTERNOON
* EVENING
* NIGHT

This data is pre-populated in the database when the application starts. 

Once the application has started, you'll notice that the relevant light transitioning will immediately be 
logged to the screen, along with the duration.
  
## Running

To run the application all you need to do is execute `gradle bootRun` in the project directory - this obviously presumes 
that you have gradle installed, and it's accessible in your `PATH` 

The user also has the ability to stop and start the processing of the traffic lights by navigating 
to the following URLs:

* [http://localhost:8080/traffic/stop](http://localhost:8080/traffic/stop)
* [http://localhost:8080/traffic/start](http://localhost:8080/traffic/start)

A message will be displayed to you confirming the successful execution of either of the requests 
(or an error if there was a failure)
  
## Testing

To test the application you need to run `gradle test`, again in the project directory. Once this completes, navigate to 
`build\reports\tests\test\index.html`. This will list all of the executed tests. 