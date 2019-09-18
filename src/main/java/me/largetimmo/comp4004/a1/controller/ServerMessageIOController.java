package me.largetimmo.comp4004.a1.controller;


import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class ServerMessageIOController {

    private ServerSocket serverSocket;

    private ServerGameManager serverGameManager;

    public ServerMessageIOController(Integer port,ServerGameManager serverGameManager)throws IOException {
        this.serverGameManager = serverGameManager;
        serverSocket = new ServerSocket(port);
        new Thread(()->{
            int socketCount = 0;
            try {
                while (true){
                    if(socketCount==3){
                        //All three players are ready
                        break;
                    }
                    Socket s = serverSocket.accept();
                    socketCount++;
                    serverGameManager.initPlayer(s);
                }

            } catch (IOException e) {
                log.error("Failed to established connection with client.");
            }
        }).start();
    }

}
