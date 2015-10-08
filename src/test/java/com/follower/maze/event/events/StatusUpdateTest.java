package com.follower.maze.event.events;

import com.follower.maze.users.User;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StatusUpdateTest {

    private final User newUserTwo = mock(User.class);
    private final HashMap<Integer, User> oneUser = new HashMap<Integer, User>() {{
        put(2, newUserTwo);
    }};
    private final StatusUpdate statusUpdate = new StatusUpdate(1, "1|S|2", 2);
    private final HashMap<Integer, User> users = new HashMap<>();

    @Test
    public void testNotifyUsers() throws Exception {

        statusUpdate.notifyUsers(oneUser);

        verify(newUserTwo).notifyFollowers("1|S|2");
    }

    @Test
    public void testNotifyNoUserComesBackWithRightSize() throws Exception {

        statusUpdate.notifyUsers(users);

        assertEquals(1, users.size());
    }

    @Test
    public void testNotifyNoUserHasTheRightKeys() throws Exception {

        statusUpdate.notifyUsers(users);

        assertTrue(users.containsKey(2));
    }
}