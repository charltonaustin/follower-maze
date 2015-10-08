package com.follower.maze;

public class Logger {
    public static void log(Object loggingObjects, String message) {
        final String simpleName = loggingObjects.getClass().getSimpleName();
        System.out.println("[" + simpleName + "] " + message);
    }

}
