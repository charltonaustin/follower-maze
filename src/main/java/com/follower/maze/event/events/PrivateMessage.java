package com.follower.maze.event.events;

import com.follower.maze.Logger;
import com.follower.maze.users.User;

import java.io.IOException;
import java.util.Map;

public class PrivateMessage extends Event {

    private final int sequenceNumber;
    private final int fromUserId;
    private final int toUserId;
    private final String event;

    public PrivateMessage(int sequenceNumber, String event, int fromUserId, int toUserId) {
        super(sequenceNumber);
        this.sequenceNumber = sequenceNumber;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.event = event;
    }

    @Override
    public void notifyUsers(Map<Integer, User> users) throws IOException {
        User toNewUser = users.get(toUserId);

        Logger.log(this, "sending " + event + " to " + toNewUser);

        if (toNewUser.receiveEvent(event)) {
            Logger.log(this, "had error sending to " + toNewUser.getUserNumber());
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
