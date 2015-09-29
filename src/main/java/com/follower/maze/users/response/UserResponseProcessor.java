package com.follower.maze.users.response;

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

public class UserResponseProcessor implements Processor{
    private final ConcurrentHashMap<Integer, User> users;
    private final AtomicBoolean continueRunning;
    private final BlockingQueue<Event> dispatchedEvents;

    public UserResponseProcessor(ConcurrentHashMap<Integer, User> users, AtomicBoolean continueRunning, BlockingQueue<Event> dispatchedEvents) {
        this.users = users;
        this.continueRunning = continueRunning;
        this.dispatchedEvents = dispatchedEvents;
    }


    public void process(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null && continueRunning.get()) {
            System.out.println(" inputLine = " + inputLine);
        }
    }
}
