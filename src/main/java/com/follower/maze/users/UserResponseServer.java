package com.follower.maze.users;

import com.follower.maze.MyServer;

import java.io.IOException;
import java.net.ServerSocket;

public class UserResponseServer extends MyServer {

    private final ServerSocket userClient;

    public UserResponseServer(ServerSocket userClient) {
        this.userClient = userClient;
    }

    @Override
    public void run() {

    }

    @Override
    public void shutDown() throws IOException {

    }
}
