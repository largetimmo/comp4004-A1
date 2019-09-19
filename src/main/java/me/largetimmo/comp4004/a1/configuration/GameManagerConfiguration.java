package me.largetimmo.comp4004.a1.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
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
    public ServerMessageIOController getServerMessageIOController(@Value("${server.port}") Integer port,ServerGameManager serverGameManager) throws IOException {
        return new ServerMessageIOController(port,serverGameManager);
    }

    @Bean
    @ConditionalOnProperty(name = "app.mode",havingValue = "client")
    public ClientMessageIOController getClientMessageIOController(@Value("${server.host}")String host,
                                                                  @Value("${server.port}")Integer port,
                                                                  ClientGameManager clientGameManager) throws IOException{
        return new ClientMessageIOController(host,port,clientGameManager);
    }

    @Bean
    @ConditionalOnProperty(name = "app.mode",havingValue = "server")
    public ServerGameManager getServerGameManager(ObjectMapper mapper){
        return new ServerGameManager(mapper);
    }

    @Bean
    @ConditionalOnProperty(name = "app.mode",havingValue = "client")
    public ClientGameManager getClientGameManager(ObjectMapper objectMapper,PlayerDTOMapper playerDTOMapper){
        return new ClientGameManager(objectMapper,playerDTOMapper);
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
