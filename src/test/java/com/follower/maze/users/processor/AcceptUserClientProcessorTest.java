package com.follower.maze.users.processor;

import com.follower.maze.users.User;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptUserClientProcessorTest {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicBoolean continueRunning = new AtomicBoolean(true);
    private final Socket socketMock = mock(Socket.class);
    private final Set<User> followers = new ConcurrentSkipListSet<>();
    private final User user = new User(2932, null, followers);
    private final PrintStream printStreamMock = mock(PrintStream.class);
    private final AcceptUserClientProcessor acceptUserProcessor = new AcceptUserClientProcessor(continueRunning, users, printStreamMock);

    @Test
    public void testProcessANewUser() throws Exception {
        when(socketMock.getInputStream()).thenReturn(IOUtils.toInputStream("2932\\r\\n"));
        when(socketMock.getOutputStream()).thenReturn(System.out);
        acceptUserProcessor.process(socketMock);

        assertEquals(users.get(2932), user);
    }

    @Test
    public void testProcessAnInvalidUser() throws Exception {
        when(socketMock.getInputStream()).thenReturn(IOUtils.toInputStream("asdf\\r\\n"));
        when(socketMock.getOutputStream()).thenReturn(System.out);

        acceptUserProcessor.process(socketMock);

        verify(printStreamMock).println("Could not parse input: asdf\\r\\n");
        // TODO: figure out how the api works
//        verify(printStreamMock).println(matches("Threw NumberFormatException e:"));
    }

    // TODO: figure out if this is possible
    @Test
    @Ignore
    public void testProcessANullUser() throws Exception {
        when(socketMock.getInputStream()).thenReturn(IOUtils.toInputStream("null\\r\\n"));
        when(socketMock.getOutputStream()).thenReturn(System.out);

        acceptUserProcessor.process(socketMock);

        verify(printStreamMock).println("Could not parse input: asdf\\r\\n");
//        verify(printStreamMock).println(matches("Threw NumberFormatException e:"));
    }


}