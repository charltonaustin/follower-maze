package com.follower.maze.event.events;

import com.follower.maze.users.User;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BroadcastTest {

    @Test
    public void testNotifyUsers() throws Exception {
        final Broadcast broadcast = new Broadcast(1, "1|B");
        final User mock = mock(User.class);
        final HashMap<Integer, User> users = new HashMap<Integer, User>() {{
            put(1, mock);
            put(2, mock);
            put(3, mock);
            put(4, null);
        }};
        broadcast.notifyUsers(users);

        verify(mock, times(3)).receiveEvent("1|B");
    }
}