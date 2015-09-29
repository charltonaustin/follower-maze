package com.follower.maze;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class ServerTest {

    // TODO: When you have internet get mockito and write tests for rest of the logic
    private final AtomicBoolean continueRunning = new AtomicBoolean(true);
    private ServerSocketMock socketServerMock;
    private Server server;
    private ProcessorMock processorMock = new ProcessorMock();


    @Before
    public void setUp() throws Exception {
        socketServerMock = new ServerSocketMock();
        server = new Server(Executors.newSingleThreadExecutor(), socketServerMock, continueRunning, processorMock);
    }

    @Test
    public void testRunStopsWhenContinueRunningIsSetToFalse() throws Exception {
        // Given
        final Future<?> submit = Executors.newSingleThreadExecutor().submit(server);

        // When
        continueRunning.set(false);
        submit.get(1000, TimeUnit.MILLISECONDS);

        //Then
        assertTrue(submit.isDone());
    }


    @Test
    public void testShutDown() throws Exception {
    }

    private class ProcessorMock implements Processor {


        @Override
        public void process(Socket clientSocket) throws IOException {

        }
    }

    private class ServerSocketMock extends ServerSocket {
        private boolean acceptCalled;

        public ServerSocketMock() throws IOException {
        }

        public ServerSocketMock(int port) throws IOException {
            super(port);
        }

        public ServerSocketMock(int port, int backlog) throws IOException {
            super(port, backlog);
        }

        public ServerSocketMock(int port, int backlog, InetAddress bindAddr) throws IOException {
            super(port, backlog, bindAddr);
        }
    }
}