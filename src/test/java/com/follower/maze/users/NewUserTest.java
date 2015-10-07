package com.follower.maze.users;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NewUserTest {

    private final Set<NewUser> followers = new ConcurrentSkipListSet<>();
    final BufferedWriter bufferedWriterMock = mock(BufferedWriter.class);
    final NewUser newUser = new NewUser(1, bufferedWriterMock, followers);
    final NewUser follower = new NewUser(2, bufferedWriterMock, null);

    @Test
    public void testReceiveEvent() throws IOException {
        final String event = "some event";

        newUser.receiveEvent(event);

        verify(bufferedWriterMock).write(event);
        verify(bufferedWriterMock).flush();
    }

    @Test
    public void testAddFollower() throws IOException {
        newUser.addFollower(follower);

        assertTrue(followers.contains(follower));
    }

    @Test
    public void testRemoveFollower() throws IOException {
        newUser.addFollower(follower);
        newUser.removeFollower(follower);

        assertFalse(followers.contains(follower));
    }

    @Test
    public void testNotifyFollowers() throws IOException {
        final String event = "some event";
        followers.add(follower);

        newUser.notifyFollowers(event);

        verify(bufferedWriterMock).write(event);
        verify(bufferedWriterMock).flush();
    }
}