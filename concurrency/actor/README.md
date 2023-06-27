Actor
=====

An actor is an object which has 2 channels, incoming and outgoing.
You need to make an actor which takes an HTTP status and returns 
the result of http://http.cat/${status} where ${status} is the 
incoming status.

Requirement: The incoming statuses might be a lot, but we don't 
             want to block the process, so make the actor asynchronous.

Requirement: The communication between actor and business process
             must be done via channels. Channels may block your process.
