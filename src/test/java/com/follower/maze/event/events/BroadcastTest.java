package com.follower.maze.event.events;

import com.follower.maze.users.NewUser;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BroadcastTest {

    @Test
    public void testNotifyUsers() throws Exception {
        final Broadcast broadcast = new Broadcast(1, "1|B");
        final NewUser mock = mock(NewUser.class);
        final HashMap<Integer, NewUser> users = new HashMap<Integer, NewUser>() {{
            put(1, mock);
            put(2, mock);
            put(3, mock);
            put(4, null);
        }};
        broadcast.notifyUsers(users);

        verify(mock, times(3)).receiveEvent("1|B");
    }
}