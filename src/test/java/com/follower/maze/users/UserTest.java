package com.follower.maze.users;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserTest {

    private Set<User> followers = new ConcurrentSkipListSet<>();
    private PrintWriter writer = mock(PrintWriter.class);
    private List<String> events = new LinkedList<>();
    private User user = new User(1, events, followers);
    private User follower = new User(2, events, null);

    @Test
    public void testReceiveEventAfterWriterSet() throws IOException {
        final String event = "some event";
        user.setWriter(writer);

        user.receiveEvent(event);

        verify(writer).println(event);
        verify(writer, times(2)).checkError();
    }

    @Test
    public void testReceiveEventBeforeWriterSet() throws IOException {
        final String event = "some event";

        user.receiveEvent(event);
        user.setWriter(writer);

        verify(writer).println(event);
        verify(writer).checkError();
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
        follower.setWriter(writer);

        user.notifyFollowers(event);

        verify(writer).println(event);
        verify(writer, times(2)).checkError();
    }
}