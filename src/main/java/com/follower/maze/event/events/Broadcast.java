package com.follower.maze.event.events;

import com.follower.maze.users.User;

import java.io.IOException;
import java.util.Map;

public class Broadcast extends Event {

    private final int sequenceNumber;
    private final String event;

    public Broadcast(Integer sequenceNumber, String event) {
        this.sequenceNumber = sequenceNumber;
        this.event = event;
    }

    @Override
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public void notifyUsers(Map<Integer, User> users) throws IOException {
        for (User user : users.values()) {
            if (user != null) {
                user.receiveEvent(event);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Broadcast broadcast = (Broadcast) o;

        if (sequenceNumber != broadcast.sequenceNumber) return false;
        if (event != null ? !event.equals(broadcast.event) : broadcast.event != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sequenceNumber;
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Broadcast{" +
                "sequenceNumber=" + sequenceNumber +
                ", event='" + event + '\'' +
                '}';
    }
}
