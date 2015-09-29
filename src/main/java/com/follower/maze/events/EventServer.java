package com.follower.maze.events;

import com.follower.maze.MyServer;
import com.follower.maze.Processor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EventServer extends MyServer {
    private final ServerSocket eventSource;
    private final Processor eventProcessor;

    public EventServer(ServerSocket eventSource, Processor eventProcessor) {
        this.eventSource = eventSource;
        this.eventProcessor = eventProcessor;
    }

    @Override
    public void run() {
        try {
            final Socket eventSocket = eventSource.accept();
            eventProcessor.process(eventSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() throws IOException {
        if (eventSource != null) {
            eventSource.close();
        }
    }
}
