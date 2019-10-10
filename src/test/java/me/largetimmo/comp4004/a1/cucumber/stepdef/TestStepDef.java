package me.largetimmo.comp4004.a1.cucumber.stepdef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import me.largetimmo.comp4004.a1.service.bo.ScoreSheetBO;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class TestStepDef extends AbstractStepDef implements En {

    @Autowired
    @Qualifier("userSim1")
    private BufferedWriter bw1;

    @Autowired
    private ServerGameManager serverGameManager;

    private boolean matched = false;

    public TestStepDef() {
        Given("^user roll dice$", () -> {
            bw1.newLine();
            bw1.flush();
        });
        And("user has dices:", (DataTable dices) -> {
            dices.asList().forEach(log::info);
        });
        And("user score to {int}", (Integer scoreChoice) -> {
            bw1.write("3\n");
            bw1.write(scoreChoice.toString());
            bw1.newLine();
            bw1.flush();
        });
        Then("wait for {int}",(Integer time)->{
            Thread.sleep(time*1000);
        });
        Then("scoreboard section is {int} :", (Integer score, DataTable dataTable) -> {
            PlayerBO playerBO = serverGameManager.getPlayers().get(0);
            ScoreSheetBO scoreSheetBO = playerBO.getScoreSheet();
            Map<Field,Object> scoreFields = new HashMap<>();
            Arrays.stream(scoreSheetBO.getUpperSection().getClass().getDeclaredFields()).forEach(f->scoreFields.put(f,scoreSheetBO.getUpperSection()));
            Arrays.stream(scoreSheetBO.getLowerSection().getClass().getDeclaredFields()).forEach(f->scoreFields.put(f,scoreSheetBO.getLowerSection()));
            for (Field f : scoreFields.keySet()) {
                if (f.getName().equals(dataTable.asList().get(0))) {
                    matched = true;
                    f.setAccessible(true);
                    Assert.assertEquals(score,f.get(scoreFields.get(f)));
                }
            }
            if (!matched){
                Assert.fail("Failed to match with field names. Possible typo.");
            }
        });

    }
}
