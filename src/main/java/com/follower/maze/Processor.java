package com.follower.maze;

import java.io.IOException;
import java.net.Socket;

public interface Processor {
    public void process(Socket clientSocket) throws IOException;
}
