package me.largetimmo.comp4004.a1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.ntlm.Server;
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

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class AutoTestContext {

    //For Simulation
    @Bean(name = "luckyPort1")
    public Integer getLuckyPort1(){
        return new Random().nextInt(20000)+40000;
    }

    //For real server/client
    @Bean(name = "luckyPort2")
    public Integer getLuckyPort2(){
        return new Random().nextInt(20000)+40000;
    }

    @Bean(name = "outputServer")
    public ServerSocket serverSocket(@Qualifier("luckyPort1")Integer port) throws IOException {
        return new ServerSocket(port);
    }

    @Bean(name = "user1")
    @DependsOn({"outputServer","input1"})
    public Socket user1(@Qualifier("outputServer")ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }
    @Bean(name = "user2")
    @DependsOn({"outputServer","input2"})
    public Socket user2(@Qualifier("outputServer")ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }
    @Bean(name = "user3")
    @DependsOn({"outputServer","input3"})
    public Socket user3(@Qualifier("outputServer")ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    @Bean(name = "input1")
    public InputStream input1(@Qualifier("luckyPort1")Integer port) throws IOException {
        Socket s = new Socket("127.0.0.1",port);
        return s.getInputStream();
    }
    @Bean(name = "input2")
    @DependsOn("input1")
    public InputStream input2(@Qualifier("luckyPort1")Integer port) throws IOException {
        Socket s = new Socket("127.0.0.1",port);
        return s.getInputStream();
    }
    @Bean(name = "input3")
    @DependsOn("input2")
    public InputStream input3(@Qualifier("luckyPort1")Integer port) throws IOException {
        Socket s = new Socket("127.0.0.1",port);
        return s.getInputStream();
    }

    @Bean(name = "userSim1")
    public BufferedWriter bw1 (@Qualifier("user1")Socket s) throws IOException {
        BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bufferedWriter.write("k1\ny\n");
        bufferedWriter.flush();
        return bufferedWriter;
    }
    @Bean(name = "userSim2")
    public BufferedWriter bw2 (@Qualifier("user2")Socket s) throws IOException {
        BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bufferedWriter.write("k2\ny\n");
        bufferedWriter.flush();
        return bufferedWriter;
    }
    @Bean(name = "userSim3")
    public BufferedWriter bw3 (@Qualifier("user3")Socket s) throws IOException {
        BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bufferedWriter.write("k3\ny\n");
        bufferedWriter.flush();
        return bufferedWriter;
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
    public ServerMessageIOController serverMessageIOController(ServerGameManager serverGameManager,@Qualifier("luckyPort2")Integer port) throws IOException {
        return Mockito.spy(new ServerMessageIOController(port, serverGameManager));
    }

    @Primary
    @Bean
    public ServerGameManager serverGameManager(ObjectMapper mapper, PlayerDTOMapper playerDTOMapper) {
        return Mockito.spy(new ServerGameManager(mapper, playerDTOMapper));
    }

    @Primary
    @Bean(name = "client-controller1")
    @DependsOn("serverController")
    public ClientMessageIOController clientMessageIOController(@Qualifier("client1") ClientGameManager clientGameManager,@Qualifier("luckyPort2")Integer port) throws Exception {
        return Mockito.spy(new ClientMessageIOController("localhost", port, clientGameManager));
    }

    @Primary
    @Bean(name = "client1")
    @DependsOn({"serverController","userSim1"})
    public ClientGameManager clientGameManager(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper,@Qualifier("input1")InputStream inputFile1) throws Exception {
        return Mockito.spy(new ClientGameManager(objectMapper, playerDTOMapper, (inputFile1)));
    }

    @Primary
    @Bean(name = "client-controller2")
    @DependsOn("serverController")
    public ClientMessageIOController clientMessageIOController2(@Qualifier("client2")ClientGameManager clientGameManager,@Qualifier("luckyPort2")Integer port) throws Exception {
        return Mockito.spy(new ClientMessageIOController("localhost", port, clientGameManager));
    }

    @Primary
    @Bean(name = "client2")
    @DependsOn({"serverController","userSim2"})
    public ClientGameManager clientGameManager2(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper,@Qualifier("input2")InputStream inputFile2) throws Exception {
        return Mockito.spy(new ClientGameManager(objectMapper, playerDTOMapper, (inputFile2)));
    }

    @Primary
    @Bean(name = "client-controller3")
    @DependsOn("serverController")
    public ClientMessageIOController clientMessageIOController3(@Qualifier("client3")ClientGameManager clientGameManager,@Qualifier("luckyPort2")Integer port) throws Exception {
        return Mockito.spy(new ClientMessageIOController("localhost", port, clientGameManager));
    }

    @Primary
    @Bean(name = "client3")
    @DependsOn({"serverController","userSim3"})
    public ClientGameManager clientGameManager3(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper,@Qualifier("input3")InputStream inputFile3) throws Exception {
        return Mockito.spy(new ClientGameManager(objectMapper, playerDTOMapper, (inputFile3)));
    }
}
