package com.follower.maze;

import java.io.IOException;

public abstract class MyServer implements Runnable, ShutDown {

    public abstract void run();

    public abstract void shutDown() throws IOException;
}
