package com.follower.maze;

import com.follower.maze.events.EventProcessor;
import com.follower.maze.events.EventServer;
import com.follower.maze.users.NewUserServer;
import com.follower.maze.users.User;
import com.follower.maze.users.UserResponseServer;
import com.follower.maze.users.UserProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final AtomicBoolean continueRunning = new AtomicBoolean(true);
    private static final Thread mainThread = Thread.currentThread();
    private static final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<Integer, User>(100);
    private static final Thread shutdownHook = new Thread() {
        public void run() {
            continueRunning.set(false);
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        final ServerSocket userSocketServer = new ServerSocket(9099);
        final UserResponseServer userResponseServer = new UserResponseServer(userSocketServer);
        final EventServer eventServer = new EventServer(new ServerSocket(9090), new EventProcessor(continueRunning, users, null, currentMessages));
        final NewUserServer newUserServer = new NewUserServer(
                Executors.newCachedThreadPool(),
                userSocketServer,
                continueRunning,
                new UserProcessor(users, continueRunning));

        final LinkedList<MyServer> servers = new LinkedList<MyServer>() {{
            add(newUserServer);
            add(eventServer);
            add(userResponseServer);
        }};

        new Application(
                Executors.newCachedThreadPool(),
                servers,
                continueRunning
        ).run();

        for (MyServer server : servers) {
            server.shutDown();
        }
    }
}
