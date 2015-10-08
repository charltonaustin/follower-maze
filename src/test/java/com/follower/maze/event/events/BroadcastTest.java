package com.follower.maze.event.events;

import com.follower.maze.users.User;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BroadcastTest {

    private final User userMock = mock(User.class);
    private final Broadcast broadcast = new Broadcast(1, "1|B");

    @Test
    public void testNotifyUsersWithError() throws Exception {
        final HashMap<Integer, User> users = new HashMap<Integer, User>() {{
            put(1, userMock);
        }};
        when(userMock.receiveEvent(anyString())).thenReturn(true);
        when(userMock.getUserNumber()).thenReturn(1);

        broadcast.notifyUsers(users);

        assertEquals(0, users.size());
    }

    @Test
    public void testNotifyUsers() throws Exception {
        final HashMap<Integer, User> users = new HashMap<Integer, User>() {{
            put(1, userMock);
            put(2, userMock);
            put(3, userMock);
        }};

        broadcast.notifyUsers(users);

        verify(userMock, times(3)).receiveEvent("1|B");
    }

    @Test
    public void test() throws Exception {
        final HashMap<Integer, User> users = new HashMap<Integer, User>() {{
            put(1, userMock);
            put(2, userMock);
            put(3, userMock);
        }};

        broadcast.notifyUsers(users);

        verify(userMock, times(3)).receiveEvent("1|B");
    }
}