package com.follower.maze.events;

import com.follower.maze.Processor;
import com.follower.maze.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventProcessor implements Processor {

    private final AtomicBoolean continueRunning;
    private final ConcurrentHashMap<Integer, User> users;
    private final BlockingQueue<User> readyToProcessMessages;
    private final PriorityQueue<User> currentMessages;

    public EventProcessor(AtomicBoolean continueRunning,
                          ConcurrentHashMap<Integer, User> users,
                          BlockingQueue<User> readyToProcessMessages,
                          PriorityQueue<User> currentMessages) {
        this.continueRunning = continueRunning;
        this.users = users;
        this.readyToProcessMessages = readyToProcessMessages;
        this.currentMessages = currentMessages;
    }

    public void process(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null && continueRunning.get()) {
            System.out.println("inputLine = " + inputLine);
        }
    }
}
