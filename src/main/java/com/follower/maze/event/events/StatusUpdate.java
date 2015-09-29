package com.follower.maze.event.events;

public class StatusUpdate implements Event {

    private final Integer sequenceNumber;
    private final Integer fromUserId;
    private final Integer toUserId;
    private final String event;

    public StatusUpdate(Integer sequenceNumber, String event, Integer fromUserId, Integer toUserId) {
        this.sequenceNumber = sequenceNumber;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.event = event;
    }
}
