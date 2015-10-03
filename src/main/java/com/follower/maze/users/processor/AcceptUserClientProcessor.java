package com.follower.maze.users.processor;

import com.follower.maze.ClientProcessor;
import com.follower.maze.users.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class AcceptUserClientProcessor implements ClientProcessor {
    private final ConcurrentHashMap<Integer, User> users;
    private final AtomicBoolean continueRunning;
    private final PrintStream printStream;

    public AcceptUserClientProcessor(AtomicBoolean continueRunning, ConcurrentHashMap<Integer, User> users, PrintStream printStream) {
        this.users = users;
        this.continueRunning = continueRunning;
        this.printStream = printStream;
    }

    public void process(final Socket clientSocket) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null && continueRunning.get()) {
            try {
                final int userId = Integer.parseInt(inputLine.replace("\\r\\n", ""));
                users.putIfAbsent(userId, new User(userId, bufferedWriter, new ConcurrentSkipListSet<User>()));
            } catch (NumberFormatException e) {
                printStream.println("Could not parse input: " + inputLine);
                printStream.println("Threw NumberFormatException e: " + e);
            } catch (NullPointerException e) {
                printStream.println("Parsed null number: " + inputLine);
            }
        }
    }
}
