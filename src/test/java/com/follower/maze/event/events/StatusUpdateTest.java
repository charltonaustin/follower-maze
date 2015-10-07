package com.follower.maze.event.events;

import com.follower.maze.users.NewUser;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StatusUpdateTest {

    private final NewUser newUserTwo = mock(NewUser.class);
    private final HashMap<Integer, NewUser> users = new HashMap<Integer, NewUser>() {{
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

        final HashMap<Integer, NewUser> users = new HashMap<>();

        statusUpdate.notifyUsers(users);
    }
}