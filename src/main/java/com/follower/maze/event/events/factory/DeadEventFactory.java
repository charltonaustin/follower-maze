package com.follower.maze.event.events.factory;

import com.follower.maze.event.events.DeadEvent;
import com.follower.maze.event.events.Event;

public class DeadEventFactory implements EventFactory {
    @Override
    public Event create(String[] values, String event) {
        return DeadEvent.deadEvent();
    }
}
