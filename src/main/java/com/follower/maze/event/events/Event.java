package com.follower.maze.event.events;

import com.follower.maze.users.User;

import java.io.IOException;
import java.util.Map;

public abstract class Event implements Comparable<Event> {
    public abstract int getSequenceNumber();

    public abstract void notifyUsers(Map<Integer, User> users) throws IOException;

    @Override
    public int compareTo(Event o) {
        return Integer.compare(this.getSequenceNumber(), o.getSequenceNumber());
    }
}
