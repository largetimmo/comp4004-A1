package me.largetimmo.comp4004.a1;


import me.largetimmo.comp4004.a1.controller.ClientMessageIOController;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"app.mode=client", "server.host=localhost", "server.port=37777"})
public class ClientMessageIOControllerTest {

    private static ServerSocket serverSocket;

    @Autowired
    private ClientMessageIOController controller;

    private static List<Socket> clients = new ArrayList<>();

    @BeforeClass
    public static void setupServerSocket() throws Exception {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 37777));
        new Thread(() -> {
            try {
                Socket s = serverSocket.accept();
                clients.add(s);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    @Test
    public void testClientConnection() throws Exception {
        Assert.assertTrue(controller.getConnection().isConnected());
    }
}
