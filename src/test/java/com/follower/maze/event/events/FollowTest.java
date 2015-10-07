package com.follower.maze.event.events;

import com.follower.maze.users.NewUser;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FollowTest {

    private final NewUser newUserThree = mock(NewUser.class);
    private final NewUser newUserTwo = mock(NewUser.class);
    private final HashMap<Integer, NewUser> users = new HashMap<Integer, NewUser>() {{
        put(2, newUserTwo);
        put(3, newUserThree);
    }};
    private final Follow follow = new Follow(1, "1|F|2|3", 2, 3);

    @Test
    public void testNotifyUsers() throws Exception {

        follow.notifyUsers(users);

        verify(newUserThree).receiveEvent("1|F|2|3");
    }

    @Test
    public void testFollowUserCorrectly() throws Exception {

        follow.notifyUsers(users);

        verify(newUserThree).addFollower(newUserTwo);
    }

    @Test
    public void testNotifyWhenUserIsNotThere() throws Exception {

        final HashMap<Integer, NewUser> users = new HashMap<>();

        follow.notifyUsers(users);
    }
}