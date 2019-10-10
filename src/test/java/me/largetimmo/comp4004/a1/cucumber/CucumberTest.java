package me.largetimmo.comp4004.a1.cucumber;

import io.cucumber.java.Before;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.AutoTestContext;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
@Slf4j
public class CucumberTest {

}
