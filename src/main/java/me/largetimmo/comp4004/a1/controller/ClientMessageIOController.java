package me.largetimmo.comp4004.a1.controller;

import lombok.Getter;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientMessageIOController {

    @Getter
    private Socket connection;

    public ClientMessageIOController(String serverHost, Integer serverPort, ApplicationContext context) throws IOException {
        connection = new Socket(serverHost, serverPort);
    }
}
