package com.follower.maze.event.events.factory;

import com.follower.maze.event.events.Event;

public interface EventFactory {
    Event create(String[] values, String event);
}
