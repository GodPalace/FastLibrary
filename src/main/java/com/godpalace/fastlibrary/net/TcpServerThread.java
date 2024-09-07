package com.godpalace.fastlibrary.net;

import java.net.Socket;

public interface TcpServerThread {
    void run(Socket socket);
}
