package com.follower.maze.users.processor;

import com.follower.maze.MyServer;
import com.follower.maze.event.events.Event;
import com.follower.maze.users.NewUser;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserResponseClientProcessor extends MyServer {
    private final Map<Integer, NewUser> users;
    private final AtomicBoolean continueRunning;
    private final Queue<Event> dispatchedEvents;

    public UserResponseClientProcessor(AtomicBoolean continueRunning, Map<Integer, NewUser> users, Queue<Event> dispatchedEvents) {
        this.users = users;
        this.continueRunning = continueRunning;
        this.dispatchedEvents = dispatchedEvents;
    }

    @Override
    public void run() {
        while (continueRunning.get()) {
            final Event event = dispatchedEvents.poll();
            if (event != null) {
                try {
                    event.notifyUsers(users);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void shutDown() throws IOException {
        for (NewUser newUser : users.values()) {
            newUser.shutDown();
        }
    }
}
