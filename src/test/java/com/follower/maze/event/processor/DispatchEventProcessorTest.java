package com.follower.maze.event.processor;

import com.follower.maze.event.events.DeadEvent;
import com.follower.maze.event.events.Event;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DispatchEventProcessorTest {

    private final PriorityBlockingQueue<Event> readyToProcessEvents = new PriorityBlockingQueue<>(100);
    private final PriorityBlockingQueue<Event> dispatchEvents = new PriorityBlockingQueue<>(100);
    private final AtomicBoolean continueRunning = new AtomicBoolean(true);
    private final DispatchEventProcessor dispatchEventProcessor = new DispatchEventProcessor(continueRunning, readyToProcessEvents, dispatchEvents);
    private final Event event = mock(Event.class);

    @Test
    public void testDispatchEventWhenLowerThanCurrentSequenceNumber() throws Exception {
        readyToProcessEvents.add(event);

        runDispatchEventProcessor();

        assertEquals(event, dispatchEvents.poll());
    }

    @Test
    public void testDispatchEventContinueRunningStopsLoop() throws Exception {
        readyToProcessEvents.add(event);
        continueRunning.set(false);

        runDispatchEventProcessor();

        assertNull(dispatchEvents.poll());
    }

    @Test
    public void testDispatchEvent() throws Exception {
        readyToProcessEvents.add(event);
        when(event.getSequenceNumber()).thenReturn(100);

        runDispatchEventProcessor();

        assertNull(dispatchEvents.poll());
    }

    private void runDispatchEventProcessor() throws InterruptedException, java.util.concurrent.ExecutionException {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future<?> submit = executorService.submit(dispatchEventProcessor);
        try {
            submit.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e1) {
            // Do nothing
        }
        continueRunning.set(false);
    }

}