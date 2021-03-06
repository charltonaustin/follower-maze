package com.follower.maze.event.events;

import com.follower.maze.event.events.factory.EventFactory;
import com.follower.maze.event.processor.IncomingEventProcessor;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IncomingEventProcessorTest {

    private final AtomicBoolean continueRunning = new AtomicBoolean(true);
    private final PriorityBlockingQueue<Event> readyToProcessEvents = new PriorityBlockingQueue<>(100);
    private final EventFactory eventFactoryMock = mock(EventFactory.class);
    private final Map eventTypes = mock(Map.class);
    private final Socket socketMock = mock(Socket.class);
    private final Event eventMock = mock(Event.class);
    private final IncomingEventProcessor incomingEventProcessor = new IncomingEventProcessor(continueRunning, readyToProcessEvents, eventTypes);

    @Test
    public void testProcessCreatesEvent() throws Exception {
        createIncomingMessageFrom("666|F|60|50\r\n");

        incomingEventProcessor.process(socketMock);

        assertEquals(eventMock, readyToProcessEvents.poll());
    }

    @Test
    public void testContinueRunningStopsLoop() throws Exception {
        createIncomingMessageFrom("666|F|60|50\r\n");
        continueRunning.set(false);

        incomingEventProcessor.process(socketMock);

        assertNull(readyToProcessEvents.poll());
    }

    @Test
    public void testProcessParsesMessageCorrectly() throws Exception {
        createIncomingMessageFrom("666|F|60|50\r\n");
        final String[] strings = new String[4];
        strings[0] = "666";
        strings[1] = "F";
        strings[2] = "60";
        strings[3] = "50";

        incomingEventProcessor.process(socketMock);

        verify(eventFactoryMock).create(strings, "666|F|60|50");
    }

    private void createIncomingMessageFrom(String input) throws IOException {
        when(eventTypes.get(anyString())).thenReturn(eventFactoryMock);
        when(eventFactoryMock.create(any(String[].class), anyString())).thenReturn(eventMock);
        when(socketMock.getInputStream()).thenReturn(IOUtils.toInputStream(input));
    }
}