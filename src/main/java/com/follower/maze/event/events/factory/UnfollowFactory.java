package com.follower.maze.event.events.factory;

import com.follower.maze.event.events.DeadEvent;
import com.follower.maze.event.events.Event;
import com.follower.maze.event.events.Unfollow;

public class UnfollowFactory implements EventFactory {

    @Override
    public Event create(String[] values, String event) {
        try {
            return new Unfollow(Integer.parseInt(values[0]), event, Integer.parseInt(values[2]), Integer.parseInt(values[3]));
        } catch (NumberFormatException e) {
            return DeadEvent.deadEvent();
        } catch (ArrayIndexOutOfBoundsException e) {
            return DeadEvent.deadEvent();
        }
    }
}
