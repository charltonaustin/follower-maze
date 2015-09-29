package com.follower.maze;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {


    private final ExecutorService serverThreadPool;
    private final List<MyServer> runnables;
    private final AtomicBoolean continueRunning;

    public Application(ExecutorService applicationThreads,
                       List<MyServer> runnables,
                       AtomicBoolean continueRunning) {
        this.serverThreadPool = applicationThreads;
        this.runnables = runnables;
        this.continueRunning = continueRunning;
    }

    public void run() {
        for (Runnable runnable : runnables) {
            serverThreadPool.submit(runnable);
        }
        while (continueRunning.get()) {
            serverThreadPool.shutdownNow();
        }

    }


}
