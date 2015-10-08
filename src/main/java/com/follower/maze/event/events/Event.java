package com.follower.maze.event.events;

import com.follower.maze.users.User;

import java.io.IOException;
import java.util.Map;

public abstract class Event implements Comparable<Event> {
    private final int sequenceNumber;

    protected Event(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public abstract void notifyUsers(Map<Integer, User> users) throws IOException;

    @Override
    public int compareTo(Event o) {
        return Integer.compare(this.getSequenceNumber(), o.getSequenceNumber());
    }
}
