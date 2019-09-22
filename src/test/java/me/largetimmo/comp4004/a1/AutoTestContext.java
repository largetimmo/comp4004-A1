package me.largetimmo.comp4004.a1;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
import me.largetimmo.comp4004.a1.controller.ClientMessageIOController;
import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class AutoTestContext {

    @Bean(name = "input1")
    public File inputFile1(){
        return TestUtil.createTempFileWithContent(Arrays.asList("Kyle1", "y","","1","0 1 2 3 4","2"));
    }
    @Bean(name = "input2")
    public File inputFile2(){
        return  TestUtil.createTempFileWithContent(Arrays.asList("Kyle2", "y"));
    }
    @Bean(name = "input3")
    public File inputFile3(){
        return TestUtil.createTempFileWithContent(Arrays.asList("Kyle3", "y"));
    }

    @Bean
    public PlayerDTOMapper playerDTOMapper(){
        return new PlayerDTOMapper();
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Primary
    @Bean("serverController")
    public ServerMessageIOController serverMessageIOController(ServerGameManager serverGameManager) throws IOException {
        return Mockito.spy(new ServerMessageIOController(36666, serverGameManager));
    }

    @Primary
    @Bean
    public ServerGameManager serverGameManager(ObjectMapper mapper, PlayerDTOMapper playerDTOMapper) {
        return Mockito.spy(new ServerGameManager(mapper, playerDTOMapper));
    }

    @Primary
    @Bean(name = "client-controller1")
    @DependsOn("serverController")
    public ClientMessageIOController clientMessageIOController(@Qualifier("client1") ClientGameManager clientGameManager) throws Exception {
        return Mockito.spy(new ClientMessageIOController("localhost", 36666, clientGameManager));
    }

    @Primary
    @Bean(name = "client1")
    @DependsOn("serverController")
    public ClientGameManager clientGameManager(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper,@Qualifier("input1")File inputFile1) throws Exception {
        return Mockito.spy(new ClientGameManager(objectMapper, playerDTOMapper, new FileInputStream(inputFile1)));
    }

    @Primary
    @Bean(name = "client-controller2")
    @DependsOn("serverController")
    public ClientMessageIOController clientMessageIOController2(@Qualifier("client2")ClientGameManager clientGameManager) throws Exception {
        return new ClientMessageIOController("localhost", 36666, clientGameManager);
    }

    @Primary
    @Bean(name = "client2")
    @DependsOn("serverController")
    public ClientGameManager clientGameManager2(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper,@Qualifier("input2")File inputFile2) throws Exception {
        return new ClientGameManager(objectMapper, playerDTOMapper,new FileInputStream(inputFile2));
    }

    @Primary
    @Bean(name = "client-controller3")
    @DependsOn("serverController")
    public ClientMessageIOController clientMessageIOController3(@Qualifier("client3")ClientGameManager clientGameManager) throws Exception {
        return new ClientMessageIOController("localhost", 36666, clientGameManager);
    }

    @Primary
    @Bean(name = "client3")
    @DependsOn("serverController")
    public ClientGameManager clientGameManager3(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper,@Qualifier("input3")File inputFile3) throws Exception {
        return new ClientGameManager(objectMapper, playerDTOMapper,new FileInputStream(inputFile3));
    }
}
