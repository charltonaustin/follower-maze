package com.follower.maze.event.events;

import com.follower.maze.users.User;

import java.io.IOException;
import java.util.Map;

public abstract class Event implements Comparable<Event> {
    public abstract Integer getSequenceNumber();

    public abstract void notifyUsers(Map<Integer, User> users) throws IOException;

    @Override
    public int compareTo(Event o) {
        return this.getSequenceNumber().compareTo(o.getSequenceNumber());
    }
}
