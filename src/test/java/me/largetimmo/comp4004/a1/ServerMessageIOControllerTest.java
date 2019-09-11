package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"server.port=39999","app.mode=server"})
public class ServerMessageIOControllerTest {

    @Autowired
    ServerMessageIOController serverMessageIOController;


    @Test
    public void testConnect() throws Exception{
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost",39999));
        Assert.assertTrue(socket.isConnected());
    }

}
