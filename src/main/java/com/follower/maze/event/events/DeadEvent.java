package com.follower.maze.event.events;

import com.follower.maze.users.NewUser;

import java.util.Map;

public class DeadEvent extends Event {

    private DeadEvent() {
    }

    private static final DeadEvent DEAD_EVENT = new DeadEvent();

    public static DeadEvent deadEvent() {
        return DEAD_EVENT;
    }

    @Override
    public int getSequenceNumber() {
        return 0;
    }

    @Override
    public void notifyUsers(Map<Integer, NewUser> users) {
    }

    @Override
    public String toString() {
        return "DeadEvent{}";
    }
}
