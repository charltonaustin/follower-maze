package com.follower.maze.event.processor;

import com.follower.maze.event.events.DeadEvent;
import com.follower.maze.event.events.Event;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;

public class DispatchEventProcessorTest {

    private final PriorityBlockingQueue<Event> readyToProcessEvents = new PriorityBlockingQueue<>(100);
    private final ArrayBlockingQueue<Event> dispatchEvents = new ArrayBlockingQueue<>(100);
    private final AtomicBoolean continueRunning = new AtomicBoolean(true);
    private final DispatchEventProcessor dispatchEventProcessor = new DispatchEventProcessor(continueRunning, readyToProcessEvents, dispatchEvents);

    @Test
    public void testDispatchEvent() throws Exception {
        final DeadEvent e = DeadEvent.deadEvent();
        readyToProcessEvents.add(e);

        runDispatchEventProcessor();


        assertEquals(e, dispatchEvents.poll());
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