package com.follower.maze.users;

import com.follower.maze.MyServer;
import com.follower.maze.Processor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class NewUserServer extends MyServer {
    private final ExecutorService userThreadPool;
    private final ServerSocket userSocketServer;
    private final AtomicBoolean continueRunning;
    private final Processor userProcessor;

    public NewUserServer(ExecutorService userThreadPool,
                         ServerSocket userSocketServer,
                         AtomicBoolean continueRunning,
                         Processor userProcessor) {
        this.userThreadPool = userThreadPool;
        this.userSocketServer = userSocketServer;
        this.continueRunning = continueRunning;
        this.userProcessor = userProcessor;
    }

    @Override
    public void run() {
        while (continueRunning.get()) {
            userThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Socket userSocket = userSocketServer.accept();
                        userProcessor.process(userSocket);
                        userSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    public void shutDown() throws IOException {
        userThreadPool.shutdownNow();
        userSocketServer.close();
    }
}
