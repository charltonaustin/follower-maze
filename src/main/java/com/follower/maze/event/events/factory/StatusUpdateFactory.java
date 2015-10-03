package com.follower.maze.event.events.factory;

import com.follower.maze.event.events.DeadEvent;
import com.follower.maze.event.events.Event;
import com.follower.maze.event.events.StatusUpdate;

public class StatusUpdateFactory implements EventFactory {

    @Override
    public Event create(String[] values, String event) {
        try {
            return new StatusUpdate(Integer.parseInt(values[0]), event, Integer.parseInt(values[2]));
        } catch (NumberFormatException e) {
            return DeadEvent.deadEvent();
        } catch (ArrayIndexOutOfBoundsException e) {
            return DeadEvent.deadEvent();
        }
    }
}
