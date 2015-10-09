# follower-maze
Dependencies are going to include JDK7 to compile the code and an internet connection

before running the tests run `$ ./gradelw copyRuntimeLibs`

To test just run `$ ./gradelw test`

To create an artifact run `$ ./gradelw distTar|distZip`

To install an artifact run `$ ./gradelw installApp`

To run via gradle run `$ ./gradelw run > log.txt`

The main entry point is in Main.java

Here the application and servers are configured and the main application is started.
There are two main domain object types. The event and the user.

I used different processors so that as much as possible users and events can be processed in parallel.

The basic flow for a user goes like this. A user connects via the AcceptUserClientProcessor which
then creates a new User object.

The basic flow for an event goes like this. An event gets read by the IncomingEventProcessor. The IncomingEventProcessor
does basic parsing before passing off the event to an EventFactory this does further parsing depending on the type of
event. The EventFactory then produces a new Event. That Event is then passed off to DispatchEventProcessor which orders
the Event before passing it off to the UserResponseClientProcessor which then dispatches the event to the users.


In my tests it took approximately 6 mins when writing to a file and 8 mins when writing to stdout on a console.
