package com.follower.maze.event.events;

import com.follower.maze.Logger;
import com.follower.maze.users.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

public class Follow extends Event {

    private final int sequenceNumber;
    private final int fromUserId;
    private final int toUserId;
    private final String event;

    public Follow(int sequenceNumber, String event, int fromUserId, int toUserId) {
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
        User toNewUser = users.get(toUserId);
        if (toNewUser == null) {
            final User noUserYet = new User(toUserId, new LinkedList<String>(), new ConcurrentSkipListSet<User>());
            users.put(toUserId, noUserYet);
            toNewUser = noUserYet;
        }
        User fromNewUser = users.get(fromUserId);
        if (fromNewUser == null) {
            final User noUserYet = new User(fromUserId, new LinkedList<String>(), new ConcurrentSkipListSet<User>());
            users.put(fromUserId, noUserYet);
            fromNewUser = noUserYet;
        }
        Logger.log(this, "sending " + event + " to " + toNewUser);
        toNewUser.receiveEvent(event);
        toNewUser.addFollower(fromNewUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Follow follow = (Follow) o;

        if (fromUserId != follow.fromUserId) return false;
        if (sequenceNumber != follow.sequenceNumber) return false;
        if (toUserId != follow.toUserId) return false;
        if (event != null ? !event.equals(follow.event) : follow.event != null) return false;

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
        return "Follow{" +
                "sequenceNumber=" + sequenceNumber +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", event='" + event + '\'' +
                '}';
    }
}
