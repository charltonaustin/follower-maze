package com.follower.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {
    public void process() {


        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final Future<?> submit1 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final ServerSocket users = new ServerSocket(9099);
                    users.setSoTimeout(3000);
                    final ExecutorService executorService = Executors.newFixedThreadPool(100);
                    for (; ; ) {
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                final Socket userSocket = users.accept();
                                    printOutMessages(userSocket, "users");
                                    userSocket.close();
                                } catch (IOException ex) {
                                    executorService.shutdown();
                                }
                            }

                        });
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        final Future<?> submit2 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final ServerSocket eventSource = new ServerSocket(9090);
                    final Socket eventSocket = eventSource.accept();
                    printOutMessages(eventSocket, "events");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        while (!submit1.isDone() && !submit2.isDone()) {
            try {
                Thread.sleep(1000);
                System.out.println("Sleeping a bit");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void printOutMessages(Socket clientSocket, String name) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(name + " inputLine = " + inputLine);
        }
    }
}
