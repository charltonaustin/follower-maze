package com.follower.maze.event.events;

import com.follower.maze.users.User;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FollowTest {

    private final User userThree = mock(User.class);
    private final User userTwo = mock(User.class);
    private final HashMap<Integer, User> users = new HashMap<Integer, User>() {{
        put(2, userTwo);
        put(3, userThree);
    }};
    private final Follow follow = new Follow(1, "1|F|2|3", 2, 3);

    @Test
    public void testNotifyUsers() throws Exception {

        follow.notifyUsers(users);

        verify(userThree).receiveEvent("1|F|2|3");
    }

    @Test
    public void testFollowUserCorrectly() throws Exception {

        follow.notifyUsers(users);

        verify(userThree).addFollower(userTwo);
    }

    @Test
    public void testNotifyWhenUserIsNotThere() throws Exception {

        final HashMap<Integer, User> users = new HashMap<>();

        follow.notifyUsers(users);
    }
}