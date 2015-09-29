package com.follower.maze.event.processor;

import com.follower.maze.Processor;
import com.follower.maze.event.events.Event;
import com.follower.maze.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class IncomingEventProcessor implements Processor {

    private final AtomicBoolean continueRunning;
    private final BlockingQueue<Event> readyToProcessEvents;
    private final ConcurrentHashMap<Integer, User> users;

    public IncomingEventProcessor(AtomicBoolean continueRunning,
                                  ConcurrentHashMap<Integer, User> users,
                                  BlockingQueue<Event> readyToProcessEvents) {
        this.continueRunning = continueRunning;
        this.users = users;
        this.readyToProcessEvents = readyToProcessEvents;
    }

    public void process(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null && continueRunning.get()) {
            System.out.println("inputLine = " + inputLine);
        }
    }
}
