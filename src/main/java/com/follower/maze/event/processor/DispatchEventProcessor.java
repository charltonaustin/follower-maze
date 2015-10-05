package com.follower.maze.event.processor;

import com.follower.maze.MyServer;
import com.follower.maze.event.events.Event;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DispatchEventProcessor extends MyServer {

    private final AtomicBoolean continueRunning;
    private final Queue<Event> readyToProcessEvents;
    private final Queue<Event> dispatchEvents;
    private int currentSequenceNumber = 1;


    public DispatchEventProcessor(
            AtomicBoolean continueRunning,
            Queue<Event> readyToProcessEvents,
            Queue<Event> dispatchEvents) {

        this.continueRunning = continueRunning;
        this.readyToProcessEvents = readyToProcessEvents;
        this.dispatchEvents = dispatchEvents;
    }

    @Override
    public void run() {
        while (continueRunning.get()) {
            final Event peek = readyToProcessEvents.peek();
            if (peek != null && peek.getSequenceNumber() <= currentSequenceNumber) {
                final Event take = readyToProcessEvents.poll();
                if (dispatchEvents.add(take)) {
                    currentSequenceNumber++;
                }
            }
        }
    }

    @Override
    public void shutDown() throws IOException {
        // Nothing to shutdown
    }

}
