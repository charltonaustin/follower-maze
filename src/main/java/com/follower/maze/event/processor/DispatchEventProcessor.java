package com.follower.maze.event.processor;

import com.follower.maze.Processor;
import com.follower.maze.event.events.Event;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DispatchEventProcessor implements Processor{

    private final AtomicBoolean continueRunning;
    private final BlockingQueue<Event> readyToProcessEvents;
    private final BlockingQueue<Event> dispatchEvents;

    public DispatchEventProcessor(
            AtomicBoolean continueRunning,
            BlockingQueue<Event> readyToProcessEvents,
            BlockingQueue<Event> dispatchEvents) {

        this.continueRunning = continueRunning;
        this.readyToProcessEvents = readyToProcessEvents;
        this.dispatchEvents = dispatchEvents;
    }

    @Override
    public void process(Socket clientSocket) throws IOException {

    }
}
