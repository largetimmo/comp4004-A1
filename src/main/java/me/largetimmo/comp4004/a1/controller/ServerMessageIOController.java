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

    private ApplicationContext context;

    public ServerMessageIOController(Integer port,ApplicationContext context)throws IOException {
        this.context = context;
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
                    if (serverGameManager == null){
                        serverGameManager = context.getBean(ServerGameManager.class);
                    }

                }

            } catch (IOException e) {
                log.error("Failed to established connection with client.");
            }
        }).start();
    }


}
