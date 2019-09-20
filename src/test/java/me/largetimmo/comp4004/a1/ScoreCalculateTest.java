package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.service.bo.UpperSectionGameScore;
import org.junit.Assert;
import org.junit.Test;

public class ScoreCalculateTest {

    @Test
    public void calculateUpperSectionBonus(){
        UpperSectionGameScore upperSectionGameScore = new UpperSectionGameScore();
        Assert.assertEquals(0, (int) upperSectionGameScore.getTotal());
        upperSectionGameScore.setSixes(36);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(36, (int) upperSectionGameScore.getTotal());
        upperSectionGameScore.setFives(25);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(61, (int) upperSectionGameScore.getTotal());
        upperSectionGameScore.setAces(2);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(98, (int) upperSectionGameScore.getTotal());
        upperSectionGameScore.setFours(8);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(106, (int) upperSectionGameScore.getTotal());

    }

}
