package com.follower.maze.event.events;

import com.follower.maze.users.User;
import org.junit.Test;
import org.mockito.internal.verification.Times;

import java.util.HashMap;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UnfollowTest {

    private final User newUserThree = mock(User.class);
    private final User newUserTwo = mock(User.class);
    private final HashMap<Integer, User> users = new HashMap<Integer, User>() {{
        put(2, newUserTwo);
        put(3, newUserThree);
    }};
    private final Unfollow unfollow = new Unfollow(1, "1|F|2|3", 2, 3);

    @Test
    public void testNotifyUsers() throws Exception {

        unfollow.notifyUsers(users);

        verify(newUserThree, new Times(0)).notifyFollowers(anyString());
    }

    @Test
    public void testFollowUserCorrectly() throws Exception {

        unfollow.notifyUsers(users);

        verify(newUserThree).removeFollower(newUserTwo);
    }

    @Test
    public void testNotifyWhenUserIsNotThere() throws Exception {

        final HashMap<Integer, User> users = new HashMap<>();

        unfollow.notifyUsers(users);
    }
}