package com.follower.maze.users.processor;

import com.follower.maze.MyServer;
import com.follower.maze.event.events.Event;
import com.follower.maze.users.User;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserResponseClientProcessor extends MyServer {
    private final ConcurrentHashMap<Integer, User> users;
    private final AtomicBoolean continueRunning;
    private final BlockingQueue<Event> dispatchedEvents;

    public UserResponseClientProcessor(AtomicBoolean continueRunning, ConcurrentHashMap<Integer, User> users, BlockingQueue<Event> dispatchedEvents) {
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
        for (User user : users.values()) {
            user.shutDown();
        }
    }
}
