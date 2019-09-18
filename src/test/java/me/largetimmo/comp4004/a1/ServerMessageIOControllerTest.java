package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.Connection;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"server.port=39998","app.mode=server"})
public class ServerMessageIOControllerTest {

    @Autowired
    ServerMessageIOController serverMessageIOController;

    @Autowired
    ServerGameManager serverGameManager;


    @Test
    public void testConnection() throws Exception{
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost",39998));
        Assert.assertTrue(socket.isConnected());
        Thread.sleep(1000);
        Assert.assertTrue(serverGameManager.getPlayers().size()==1);
        PlayerBO playerBO = serverGameManager.getPlayers().get(0);
        Assert.assertNotNull(playerBO);
        Connection connection = playerBO.getConnection();
        Assert.assertNotNull(connection);
        BufferedReader clientBR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter clientBW = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        //TEST Server send ---> client
        Thread.sleep(1000);
        Assert.assertTrue(clientBR.ready());
        Assert.assertNotNull(clientBR.readLine());

        //TEST Client send ---> Server
        clientBW.write("I am client\n");
        clientBW.flush();
        Thread.sleep(1000);
        Assert.assertTrue(connection.hasData());
        Assert.assertEquals("I am client",connection.receive());

    }

}
