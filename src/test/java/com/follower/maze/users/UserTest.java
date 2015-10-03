package com.follower.maze.users;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserTest {

    private final Set<User> followers = new ConcurrentSkipListSet<>();
    final BufferedWriter bufferedWriterMock = mock(BufferedWriter.class);
    final User user = new User(1, bufferedWriterMock, followers);
    final User follower = new User(2, bufferedWriterMock, null);

    @Test
    public void testReceiveEvent() throws IOException {
        final String event = "some event";

        user.receiveEvent(event);

        verify(bufferedWriterMock).write(event);
        verify(bufferedWriterMock).flush();
    }

    @Test
    public void testAddFollower() throws IOException {
        user.addFollower(follower);

        assertTrue(followers.contains(follower));
    }

    @Test
    public void testRemoveFollower() throws IOException {
        user.addFollower(follower);
        user.removeFollower(follower);

        assertFalse(followers.contains(follower));
    }

    @Test
    public void testNotifyFollowers() throws IOException {
        final String event = "some event";
        followers.add(follower);

        user.notifyFollowers(event);

        verify(bufferedWriterMock).write(event);
        verify(bufferedWriterMock).flush();
    }
}