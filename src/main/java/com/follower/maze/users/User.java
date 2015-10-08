package com.follower.maze.users;

import com.follower.maze.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class User implements Comparable<User> {

    private final Integer userNumber;
    private final Set<User> followers;
    private PrintWriter writer;
    private List<String> events = new LinkedList<>();

    public User(Integer userNumber, PrintWriter writer, Set<User> followers) {
        this.userNumber = userNumber;
        this.writer = writer;
        this.followers = followers;
    }

    public User(Integer userNumber, List<String> events, Set<User> followers) {
        this.events.addAll(events);
        this.followers = followers;
        this.userNumber = userNumber;
    }

    public void addFollower(User newUser) {
        followers.add(newUser);
    }

    public void removeFollower(User newUser) {
        followers.remove(newUser);
    }

    public void notifyFollowers(String event) throws IOException {
        for (User follower : followers) {
            final boolean hadError = follower.receiveEvent(event);
            if (hadError) {
                Logger.log(this, "had error sending event to " + follower.getUserNumber() + " removing from followers");
                followers.remove(follower);
            }
        }
    }

    public boolean setWriter(PrintWriter writer) throws IOException {
        this.writer = writer;
        Logger.log(this, " writing out events " + events + " toUserId=" + userNumber);
        for (String pastEvents : events) {
            writer.println(pastEvents);
        }
        return writer.checkError();
    }

    public boolean receiveEvent(String event) throws IOException {
        if (writer != null) {
            writer.println(event);
            return writer.checkError();

        } else {
            events.add(event);
            return false;
        }
    }

    public void shutDown() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }

    public Integer getUserNumber() {
        return userNumber;
    }


    @Override
    public int hashCode() {
        return userNumber != null ? userNumber.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userNumber != null ? !userNumber.equals(user.userNumber) : user.userNumber != null) return false;

        return true;
    }

    @Override
    public int compareTo(User o) {
        return userNumber.compareTo(o.userNumber);
    }

    @Override
    public String toString() {
        return "NewUser{userNumber=" + userNumber + '}';
    }
}
