package com.follower.maze.event.events;

import com.follower.maze.users.User;

import java.util.Map;

public class DeadEvent extends Event {

    private static final DeadEvent DEAD_EVENT = new DeadEvent();

    private DeadEvent() {
        super(0);
    }

    public static DeadEvent deadEvent() {
        return DEAD_EVENT;
    }

    @Override
    public void notifyUsers(Map<Integer, User> users) {
    }

    @Override
    public String toString() {
        return "DeadEvent{}";
    }
}
