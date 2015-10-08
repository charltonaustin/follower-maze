package com.follower.maze.event.events;

import com.follower.maze.users.User;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PrivateMessageTest {

    private final User newUserThree = mock(User.class);
    private final User newUserTwo = mock(User.class);
    private final HashMap<Integer, User> twoUsers = new HashMap<Integer, User>() {{
        put(2, newUserTwo);
        put(3, newUserThree);
    }};
    private final PrivateMessage privateMessage = new PrivateMessage(1, "1|F|2|3", 2, 3);
    private final HashMap<Integer, User> noUsers = new HashMap<>();

    @Test
    public void testNotifyUsers() throws Exception {

        privateMessage.notifyUsers(twoUsers);

        verify(newUserThree).receiveEvent("1|F|2|3");
    }

    @Test
    public void testNotifyWhenUserIsRightSize() throws Exception {

        privateMessage.notifyUsers(noUsers);

        assertEquals(1, noUsers.size());
    }

    @Test
    public void testNotifyWhenUserHasTheCorrectKeys() throws Exception {

        privateMessage.notifyUsers(noUsers);

        assertTrue(noUsers.containsKey(3));
    }
}