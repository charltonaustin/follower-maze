package com.follower.maze.event.events;

import com.follower.maze.Logger;
import com.follower.maze.users.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

public class StatusUpdate extends Event {

    private final int sequenceNumber;
    private final int fromUserId;
    private final String event;

    public StatusUpdate(int sequenceNumber, String event, int fromUserId) {
        super(sequenceNumber);
        this.sequenceNumber = sequenceNumber;
        this.fromUserId = fromUserId;
        this.event = event;
    }

    @Override
    public void notifyUsers(Map<Integer, User> users) throws IOException {
        User newUser = users.get(fromUserId);
        if (newUser == null) {
            final User noUserYet = new User(fromUserId, new LinkedList<String>(), new ConcurrentSkipListSet<User>());
            users.put(fromUserId, noUserYet);
            newUser = noUserYet;
        }
        Logger.log(this, "sending " + event + " to " + newUser);
        newUser.notifyFollowers(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusUpdate that = (StatusUpdate) o;

        if (fromUserId != that.fromUserId) return false;
        if (sequenceNumber != that.sequenceNumber) return false;
        if (event != null ? !event.equals(that.event) : that.event != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sequenceNumber;
        result = 31 * result + fromUserId;
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatusUpdate{" +
                "sequenceNumber=" + sequenceNumber +
                ", fromUserId=" + fromUserId +
                ", event='" + event + '\'' +
                '}';
    }
}
