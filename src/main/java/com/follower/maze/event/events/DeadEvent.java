package com.follower.maze.event.events;

import com.follower.maze.users.User;

import java.util.Map;

public class DeadEvent extends Event {

    private DeadEvent() {
    }

    private static DeadEvent deadEvent = new DeadEvent();

    public static DeadEvent deadEvent() {
        return deadEvent;
    }

    @Override
    public Integer getSequenceNumber() {
        return 0;
    }

    @Override
    public void notifyUsers(Map<Integer, User> users) {
    }

    @Override
    public String toString() {
        return "DeadEvent{}";
    }
}
