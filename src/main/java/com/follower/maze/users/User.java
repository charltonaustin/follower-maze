package com.follower.maze.users;

public class User implements Comparable<User> {

    private final Integer userNumber;

    public User(Integer userNumber) {
        this.userNumber = userNumber;
    }

    @Override
    public int compareTo(User o) {
        return userNumber.compareTo(o.userNumber);
    }
}
