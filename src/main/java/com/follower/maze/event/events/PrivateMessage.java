package com.follower.maze.event.events;

import com.follower.maze.users.User;

import java.io.IOException;
import java.util.Map;

public class PrivateMessage extends Event {

    private final int sequenceNumber;
    private final int fromUserId;
    private final int toUserId;
    private final String event;

    public PrivateMessage(int sequenceNumber, String event, int fromUserId, int toUserId) {
        this.sequenceNumber = sequenceNumber;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.event = event;
    }

    @Override
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public void notifyUsers(Map<Integer, User> users) throws IOException {
        final User toUser = users.get(toUserId);
        if (toUser != null) {
            toUser.receiveEvent(event);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateMessage that = (PrivateMessage) o;

        if (fromUserId != that.fromUserId) return false;
        if (sequenceNumber != that.sequenceNumber) return false;
        if (toUserId != that.toUserId) return false;
        if (event != null ? !event.equals(that.event) : that.event != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sequenceNumber;
        result = 31 * result + fromUserId;
        result = 31 * result + toUserId;
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "sequenceNumber=" + sequenceNumber +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", event='" + event + '\'' +
                '}';
    }
}
