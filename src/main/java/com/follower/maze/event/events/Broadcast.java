package com.follower.maze.event.events;

public class Broadcast implements Event{

    private final Integer sequenceNumber;
    private final String event;

    public Broadcast(Integer sequenceNumber, String event) {
        this.sequenceNumber = sequenceNumber;
        this.event = event;
    }
}
