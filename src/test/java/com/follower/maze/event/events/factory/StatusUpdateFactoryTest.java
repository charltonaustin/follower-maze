package com.follower.maze.event.events.factory;

import com.follower.maze.event.events.DeadEvent;
import com.follower.maze.event.events.Event;
import com.follower.maze.event.events.StatusUpdate;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StatusUpdateFactoryTest {

    public static final String TYPE = "S";
    private final EventFactory factory = new StatusUpdateFactory();
    private final DeadEvent expectedDeadEvent = DeadEvent.deadEvent();

    @Test
    public void test() throws Exception {
        final String eventString = "666|" + TYPE + "|60";
        String[] values = eventString.split("\\|");

        final Event event = factory.create(values, eventString);

        assertEquals(new StatusUpdate(666, eventString, 60), event);
    }

    @Test
    public void testInvalidSequenceEvent() throws Exception {
        final String eventString = "adfasd|" + TYPE + "|60|50";
        String[] values = eventString.split("\\|");

        final Event event = factory.create(values, eventString);

        assertEquals(expectedDeadEvent, event);
    }

    @Test
    public void testInvalidToEvent() throws Exception {
        final String eventString = "666|" + TYPE + "|asdfd|50";
        String[] values = eventString.split("\\|");

        final Event event = factory.create(values, eventString);

        assertEquals(expectedDeadEvent, event);
    }

    @Test
    public void testInvalidNumberOfAttributes() throws Exception {
        final String eventString = "666|" + TYPE;
        String[] values = eventString.split("\\|");

        final Event event = factory.create(values, eventString);

        assertEquals(expectedDeadEvent, event);
    }
}