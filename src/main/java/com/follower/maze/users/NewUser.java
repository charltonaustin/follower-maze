package com.follower.maze.users;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

public class NewUser implements Comparable<NewUser> {

    private final Integer userNumber;
    private final BufferedWriter bufferedWriter;
    private final Set<NewUser> followers;

    public NewUser(Integer userNumber, BufferedWriter bufferedWriter, Set<NewUser> followers) {
        this.userNumber = userNumber;
        this.bufferedWriter = bufferedWriter;
        this.followers = followers;
    }

    public void addFollower(NewUser newUser) {
        followers.add(newUser);
    }

    public void removeFollower(NewUser newUser) {
        followers.remove(newUser);
    }

    public void notifyFollowers(String event) throws IOException {
        for (NewUser follower : followers) {
            follower.receiveEvent(event);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUser newUser = (NewUser) o;

        if (userNumber != null ? !userNumber.equals(newUser.userNumber) : newUser.userNumber != null) return false;

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
    public int compareTo(NewUser o) {
        return userNumber.compareTo(o.userNumber);
    }
}
