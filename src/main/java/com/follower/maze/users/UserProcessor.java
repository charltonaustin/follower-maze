package com.follower.maze.users;

import com.follower.maze.Processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserProcessor implements Processor {
    private final ConcurrentHashMap<Integer, User> users;
    private final AtomicBoolean continueRunning;

    public UserProcessor(ConcurrentHashMap<Integer, User> users, AtomicBoolean continueRunning) {
        this.users = users;
        this.continueRunning = continueRunning;
    }

    public void process(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null && continueRunning.get()) {
            System.out.println(" inputLine = " + inputLine);
        }
    }
}
