package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.service.ClientGameManager;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties ={"app.mode=client"} )
public class ClientGameModeTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void startServerMode() {
        context.getBean(ClientGameManager.class);
    }

}
