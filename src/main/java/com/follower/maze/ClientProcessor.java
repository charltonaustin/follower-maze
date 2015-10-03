package com.follower.maze;

import java.io.IOException;
import java.net.Socket;

public interface ClientProcessor {
    void process(Socket clientSocket) throws IOException;
}
