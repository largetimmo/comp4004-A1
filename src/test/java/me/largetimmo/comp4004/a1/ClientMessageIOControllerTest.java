package me.largetimmo.comp4004.a1;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.controller.ClientMessageIOController;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
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

    @Autowired
    private ClientGameManager clientGameManager;

    private static File inputFile;

    private static List<Socket> clients = new ArrayList<>();

    @BeforeClass
    public static void setupServerSocket() throws Exception {
        inputFile= File.createTempFile("clientSimInput","txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile));
        bw.write("Kyle");
        bw.newLine();
        bw.write("Y");
        bw.newLine();
        bw.flush();
        bw.close();
        InputStream is = new FileInputStream(inputFile);
        System.setIn(is);
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 37777));
        new Thread(() -> {
            try {
                Socket s = serverSocket.accept();
                clients.add(s);
                BasicDTO dto = new BasicDTO();
                dto.setData("111");
                dto.setType("String");
                dto.setAction(DTOAction.HELLO);
                BufferedWriter bwClient = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                bwClient.write(new ObjectMapper().writeValueAsString(dto));
                bwClient.newLine();
                bwClient.flush();
                bwClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
    @Test
    public void testClientConnection() throws Exception {

        Assert.assertTrue(controller.getConnection().isConnected());
        Thread.sleep(1000);
        Assert.assertNotNull(clientGameManager.getCurrentPlayer());
        Assert.assertNotNull(clientGameManager.getCurrentPlayer().getPlayerId());
    }
}
