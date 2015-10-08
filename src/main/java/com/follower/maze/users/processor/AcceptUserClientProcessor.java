package com.follower.maze.users.processor;

import com.follower.maze.ClientProcessor;
import com.follower.maze.Logger;
import com.follower.maze.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class AcceptUserClientProcessor implements ClientProcessor {
    private final Map<Integer, User> users;
    private final AtomicBoolean continueRunning;

    public AcceptUserClientProcessor(AtomicBoolean continueRunning, Map<Integer, User> users) {
        this.users = users;
        this.continueRunning = continueRunning;
    }

    public void process(final Socket clientSocket) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        final PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null && continueRunning.get()) {
            try {
                final int userId = Integer.parseInt(inputLine.replace("\\r\\n", ""));
                final User noUserYet = users.get(userId);
                if (noUserYet != null) {
                    noUserYet.setWriter(writer);
                    Logger.log(this, "adding buffer writer to " + noUserYet);

                } else {
                    final User newUser = new User(userId, writer, new ConcurrentSkipListSet<User>());
                    users.put(userId, newUser);
                    Logger.log(this, "adding new user " + newUser);
                }
            } catch (NumberFormatException e) {
                Logger.log(this, "Could not parse input: " + inputLine);
                Logger.log(this, "Threw NumberFormatException e: " + e);
            } catch (NullPointerException e) {
                Logger.log(this, "Parsed null number: " + inputLine);
            }
        }
    }
}
