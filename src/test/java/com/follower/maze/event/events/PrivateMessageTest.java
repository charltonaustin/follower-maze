package com.follower.maze.event.events;

import com.follower.maze.users.User;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PrivateMessageTest {

    private final User newUserThree = mock(User.class);
    private final User newUserTwo = mock(User.class);
    private final HashMap<Integer, User> users = new HashMap<Integer, User>() {{
        put(2, newUserTwo);
        put(3, newUserThree);
    }};
    private final PrivateMessage privateMessage = new PrivateMessage(1, "1|F|2|3", 2, 3);

    @Test
    public void testNotifyUsers() throws Exception {

        privateMessage.notifyUsers(users);

        verify(newUserThree).receiveEvent("1|F|2|3");
    }

    @Test
    public void testNotifyWhenUserIsNotThere() throws Exception {

        final HashMap<Integer, User> users = new HashMap<>();

        privateMessage.notifyUsers(users);
    }
}