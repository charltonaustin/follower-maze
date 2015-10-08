package com.follower.maze;

import com.follower.maze.event.events.Event;
import com.follower.maze.event.events.factory.BroadcastFactory;
import com.follower.maze.event.events.factory.EventFactory;
import com.follower.maze.event.events.factory.FollowFactory;
import com.follower.maze.event.events.factory.PrivateMessageFactory;
import com.follower.maze.event.events.factory.StatusUpdateFactory;
import com.follower.maze.event.events.factory.UnfollowFactory;
import com.follower.maze.event.processor.DispatchEventProcessor;
import com.follower.maze.event.processor.IncomingEventProcessor;
import com.follower.maze.users.User;
import com.follower.maze.users.processor.AcceptUserClientProcessor;
import com.follower.maze.users.processor.UserResponseClientProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final AtomicBoolean continueRunning = new AtomicBoolean(true);
    private static final Thread mainThread = Thread.currentThread();
    private static final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>(100);
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

    public static void main(String[] args) throws IOException, InterruptedException {
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        final ConcurrentHashMap<String, EventFactory> eventTypes = new ConcurrentHashMap<String, EventFactory>() {{
            put("F", new FollowFactory());
            put("U", new UnfollowFactory());
            put("B", new BroadcastFactory());
            put("P", new PrivateMessageFactory());
            put("S", new StatusUpdateFactory());
        }};
        final PriorityBlockingQueue<Event> readyToProcessEvents = new PriorityBlockingQueue<>();
        final PriorityBlockingQueue<Event> dispatchedEvents = new PriorityBlockingQueue<>();
        final ServerSocket userSocketServer = new ServerSocket(9099);
        userSocketServer.setSoTimeout(1000);
        final ServerSocket socket = new ServerSocket(9090);
        socket.setSoTimeout(1000);
        final Server eventServer = new Server(
                continueRunning,
                Executors.newCachedThreadPool(),
                socket,
                new IncomingEventProcessor(continueRunning, readyToProcessEvents, eventTypes));

        final Server newUserServer = new Server(
                continueRunning,
                Executors.newCachedThreadPool(),
                userSocketServer,
                new AcceptUserClientProcessor(continueRunning, users));

        final UserResponseClientProcessor userResponseClientProcessor =
                new UserResponseClientProcessor(continueRunning, users, dispatchedEvents);

        final DispatchEventProcessor dispatchEventProcessor =
                new DispatchEventProcessor(continueRunning, readyToProcessEvents, dispatchedEvents);

        final List<MyServer> servers = new LinkedList<MyServer>() {{
            add(newUserServer);
            add(eventServer);
            add(dispatchEventProcessor);
            add(userResponseClientProcessor);
        }};


        new Application(
                continueRunning,
                Executors.newCachedThreadPool(),
                servers
        ).run();

        for (MyServer server : servers) {
            server.shutDown();
        }
    }
}
