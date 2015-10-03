package com.follower.maze.event.processor;

import com.follower.maze.ClientProcessor;
import com.follower.maze.event.events.Event;
import com.follower.maze.event.events.factory.EventFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class IncomingEventProcessor implements ClientProcessor {

    private static final String DEFAULT_KEY = "Default Type Key";

    private final Map<String, EventFactory> eventTypes;
    private final AtomicBoolean continueRunning;
    private final PriorityBlockingQueue<Event> readyToProcessEvents;

    public IncomingEventProcessor(AtomicBoolean continueRunning,
                                  PriorityBlockingQueue<Event> readyToProcessEvents,
                                  Map<String, EventFactory> eventTypes) {
        this.continueRunning = continueRunning;
        this.readyToProcessEvents = readyToProcessEvents;
        this.eventTypes = eventTypes;
    }

    public void process(final Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null && continueRunning.get()) {
            final String[] split = inputLine.split("\\|");
            final String type = split.length > 2 ? split[1] : DEFAULT_KEY;
            final EventFactory eventFactory = eventTypes.get(type);
            final Event event = eventFactory.create(split, inputLine);
            readyToProcessEvents.offer(event);
        }
    }
}
