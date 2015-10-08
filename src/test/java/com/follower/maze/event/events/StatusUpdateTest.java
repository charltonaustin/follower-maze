package com.follower.maze.event.events;

import com.follower.maze.users.User;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StatusUpdateTest {

    private final User newUserTwo = mock(User.class);
    private final HashMap<Integer, User> users = new HashMap<Integer, User>() {{
        put(2, newUserTwo);
    }};
    private final StatusUpdate statusUpdate = new StatusUpdate(1, "1|S|2", 2);

    @Test
    public void testNotifyUsers() throws Exception {

        statusUpdate.notifyUsers(users);

        verify(newUserTwo).notifyFollowers("1|S|2");
    }

    @Test
    public void testNotifyWhenUserIsNotThere() throws Exception {

        final HashMap<Integer, User> users = new HashMap<>();

        statusUpdate.notifyUsers(users);
    }
}