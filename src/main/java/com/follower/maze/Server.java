package com.follower.maze;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server extends MyServer {
    private final ExecutorService userThreadPool;
    private final ServerSocket socket;
    private final AtomicBoolean continueRunning;
    private final ClientProcessor clientProcessor;

    public Server(AtomicBoolean continueRunning, ExecutorService userThreadPool,
                  ServerSocket socket,
                  ClientProcessor clientProcessor) {
        this.userThreadPool = userThreadPool;
        this.socket = socket;
        this.continueRunning = continueRunning;
        this.clientProcessor = clientProcessor;
    }

    @Override
    public void run() {
        while (continueRunning.get()) {
            final Socket userSocket;
            try {
                userSocket = socket.accept();
                userThreadPool.submit(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            clientProcessor.process(userSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown() throws IOException {
        userThreadPool.shutdownNow();
        socket.close();
    }
}
