package com.follower.maze.event.processor;

import com.follower.maze.MyServer;
import com.follower.maze.event.events.Event;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DispatchEventProcessor extends MyServer {

    private final AtomicBoolean continueRunning;
    private final PriorityBlockingQueue<Event> readyToProcessEvents;
    private final BlockingQueue<Event> dispatchEvents;
    private int currentSequenceNumber = 100;


    public DispatchEventProcessor(
            AtomicBoolean continueRunning,
            PriorityBlockingQueue<Event> readyToProcessEvents,
            BlockingQueue<Event> dispatchEvents) {

        this.continueRunning = continueRunning;
        this.readyToProcessEvents = readyToProcessEvents;
        this.dispatchEvents = dispatchEvents;
    }

    @Override
    public void run() {
        while (continueRunning.get()) {
            final Event peek = readyToProcessEvents.peek();
            if (peek != null && peek.getSequenceNumber() <= currentSequenceNumber) {
                try {
                    final Event take = readyToProcessEvents.poll();
                    dispatchEvents.put(take);
                    currentSequenceNumber++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void shutDown() throws IOException {
        // Nothing to shutdown
    }

}
