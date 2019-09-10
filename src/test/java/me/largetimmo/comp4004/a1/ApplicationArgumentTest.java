package me.largetimmo.comp4004.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ApplicationArgumentTest {

    @Test
    public void testApplicationArgumentTestWithEmptyArgument() throws Exception {
        List<String> args = new ArrayList<>();
        Properties expectedProperty = null;
        Assert.assertEquals(A1Application.getProperties((String[]) args.toArray()),expectedProperty);
    }
}
