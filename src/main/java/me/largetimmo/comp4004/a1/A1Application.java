package me.largetimmo.comp4004.a1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

@Slf4j
@SpringBootApplication
public class A1Application {

    public static void main(String[] args) {
        Properties properties = getProperties(args);
        if (properties == null){
            return;
        }
        new SpringApplicationBuilder(A1Application.class).properties(properties).run(args);
    }

    public static Properties getProperties(String[] args){
        Properties properties = new Properties();
        if (args.length > 3 || args.length < 2) {
            log.error("ERROR: Too many/few arguments");
            return null;
        }
        String serverMode = args[0];
        if ("server".equals(serverMode)) {
            if (args.length != 2) {
                log.error("ERROR: Server mode take 2 arguments ONLY");
                return null;
            }
            properties.put("app.mode", "server");
            properties.put("server.port", args[1]);
        } else if ("client".equals(serverMode)) {
            if (args.length != 3) {
                log.error("ERROR: Client mode take 3 arguments ONLY");
                return null;
            }
            properties.put("app.mode","client");
            properties.put("server.host",args[1]);
            properties.put("server.port",args[2]);
        }else {
            return null;
        }
        return properties;
    }
}
