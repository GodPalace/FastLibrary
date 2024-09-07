package com.godpalace.fastlibrary.net;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TcpServer {
    private ThreadPoolExecutor executor;
    private Thread receiverThread;
    private TcpServerThread receiver;

    private int port;
    private String ip;

    private boolean running = false;

    public TcpServer() {
        this("127.0.0.1", 3080, null);
    }

    public TcpServer(String ip, int port, TcpServerThread receiver) {
        this.ip = ip;
        this.port = port;

        executor = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000));

        this.receiver = receiver;
    }

    public void setReceiver(TcpServerThread receiver) {
        this.receiver = receiver;
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor.shutdown();
        this.executor.shutdownNow();

        this.executor = executor;
    }

    public void bind(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() {
        running = true;

        receiverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port, 10000,
                    InetAddress.getByName(ip))) {

                while (running) {
                    try {
                        Socket socket = serverSocket.accept();

                        if (receiver != null) {
                            executor.execute(() -> receiver.run(socket));
                        } else {
                            throw new RuntimeException("No receiver set");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void stop() {
        running = false;
        executor.shutdown();
        executor.shutdownNow();
    }
}
