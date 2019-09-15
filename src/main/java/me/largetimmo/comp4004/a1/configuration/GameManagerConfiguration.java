package me.largetimmo.comp4004.a1.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.largetimmo.comp4004.a1.controller.ClientMessageIOController;
import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GameManagerConfiguration {

    @Bean
    @ConditionalOnProperty(name = "app.mode",havingValue = "server")
    public ServerMessageIOController getServerMessageIOController(@Value("${server.port}") Integer port, ApplicationContext context) throws IOException {
        return new ServerMessageIOController(port,context);
    }

    @Bean
    @ConditionalOnProperty(name = "app.mode",havingValue = "client")
    public ClientMessageIOController getClientMessageIOController() throws IOException{
        return new ClientMessageIOController();
    }

    @Bean
    @ConditionalOnProperty(name = "app.mode",havingValue = "server")
    public ServerGameManager getServerGameManager(ServerMessageIOController serverMessageIOController){
        return new ServerGameManager(serverMessageIOController);
    }

    @Bean
    @ConditionalOnProperty(name = "app.mode",havingValue = "client")
    public ClientGameManager getClientGameManager(){
        return new ClientGameManager();
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
