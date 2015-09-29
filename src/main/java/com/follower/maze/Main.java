package com.follower.maze;

import com.follower.maze.event.processor.IncomingEventProcessor;
import com.follower.maze.users.User;
import com.follower.maze.users.accept.AcceptUserProcessor;
import com.follower.maze.users.response.UserResponseProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
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

        final Server eventServer = new Server(
                Executors.newCachedThreadPool(),
                new ServerSocket(9090),
                continueRunning,
                new IncomingEventProcessor(continueRunning, users, null));

        final Server newUserServer = new Server(
                Executors.newCachedThreadPool(),
                userSocketServer,
                continueRunning,
                new AcceptUserProcessor(users, continueRunning));

        final Server userResponseServer = new Server(
                Executors.newCachedThreadPool(),
                userSocketServer,
                continueRunning,
                new UserResponseProcessor(users, continueRunning, null));

        final List<MyServer> servers = new LinkedList<MyServer>() {{
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
