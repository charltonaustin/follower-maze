package com.follower.maze.event.events.factory;

import com.follower.maze.event.events.Broadcast;
import com.follower.maze.event.events.DeadEvent;
import com.follower.maze.event.events.Event;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BroadcastFactoryTest {

    private final EventFactory factory = new BroadcastFactory();
    private static final String TYPE = "B";

    @Test
    public void testValidEvent() throws Exception {

        final String eventString = "666|" + TYPE;
        String[] values = eventString.split("\\|");

        final Event event = factory.create(values, eventString);

        assertEquals(new Broadcast(666, eventString), event);
    }

    @Test
    public void testInvalidEvent() throws Exception {
        final String eventString = "adfasd|" + TYPE;
        String[] values = eventString.split("\\|");

        final Event event = factory.create(values, eventString);

        assertEquals(DeadEvent.deadEvent(), event);
    }


}