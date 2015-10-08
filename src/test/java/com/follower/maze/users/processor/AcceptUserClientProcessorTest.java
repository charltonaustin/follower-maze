package com.follower.maze.users.processor;

import com.follower.maze.users.User;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptUserClientProcessorTest {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicBoolean continueRunning = new AtomicBoolean(true);
    private final Socket socketMock = mock(Socket.class);
    private final User userMock = mock(User.class);
    private final AcceptUserClientProcessor acceptUserProcessor = new AcceptUserClientProcessor(continueRunning, users);


    public void setUpSocketWith(String input) throws IOException {
        when(socketMock.getInputStream()).thenReturn(IOUtils.toInputStream(input));
        when(socketMock.getOutputStream()).thenReturn(System.out);
    }

    @Test
    public void testProcessANewUser() throws Exception {
        setUpSocketWith("2932\\r\\n");

        acceptUserProcessor.process(socketMock);

        assertEquals(1, users.size());
    }

    @Test
    public void testContinueStopsLoop() throws Exception {
        setUpSocketWith("2932\\r\\n");
        continueRunning.set(false);

        acceptUserProcessor.process(socketMock);

        assertEquals(0, users.size());
    }

    @Test
    public void testProcessANewUserThatAlreadyExists() throws Exception {
        setUpSocketWith("2932\\r\\n");
        users.put(2932, userMock);

        acceptUserProcessor.process(socketMock);

        verify(userMock).setWriter(any(PrintWriter.class));
    }

    @Test
    public void testProcessAnInvalidUserDoesNotThrowException() throws Exception {
        setUpSocketWith("asdf\\r\\n");

        acceptUserProcessor.process(socketMock);
    }

    @Test
    public void testProcessANullUserDoeNotThrowException() throws Exception {
        setUpSocketWith("null\\r\\n");

        acceptUserProcessor.process(socketMock);

    }


}