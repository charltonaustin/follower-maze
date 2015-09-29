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
    private final Processor processor;

    public Server(ExecutorService userThreadPool,
                  ServerSocket socket,
                  AtomicBoolean continueRunning,
                  Processor processor) {
        this.userThreadPool = userThreadPool;
        this.socket = socket;
        this.continueRunning = continueRunning;
        this.processor = processor;
    }

    @Override
    public void run() {
        while (continueRunning.get()) {
            userThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Socket userSocket = socket.accept();
                        processor.process(userSocket);
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
        socket.close();
    }
}
