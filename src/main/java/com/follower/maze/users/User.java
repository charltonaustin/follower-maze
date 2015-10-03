package com.follower.maze.users;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

public class User implements Comparable<User>{

    private final Integer userNumber;
    private final BufferedWriter bufferedWriter;
    private final Set<User> followers;

    public User(Integer userNumber, BufferedWriter bufferedWriter, Set<User> followers) {
        this.userNumber = userNumber;
        this.bufferedWriter = bufferedWriter;
        this.followers = followers;
    }

    public void addFollower(User user) {
        followers.add(user);
    }

    public void removeFollower(User user) {
        followers.remove(user);
    }

    public void notifyFollowers(String event) throws IOException {
        for (User follower : followers) {
            follower.receiveEvent(event);
        }
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
    public int hashCode() {
        return userNumber != null ? userNumber.hashCode() : 0;
    }

    public void receiveEvent(String event) throws IOException {
        bufferedWriter.write(event);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public void shutDown() throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }

    @Override
    public int compareTo(User o) {
        return userNumber.compareTo(o.userNumber);
    }
}
