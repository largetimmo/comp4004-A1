package me.largetimmo.comp4004.a1;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ApplicationArgumentTest {

    @Test
    public void testApplicationArgumentTestWithEmptyArgument() throws Exception {
        Properties expectedProperty = null;
        Assert.assertEquals(A1Application.getProperties(new String[]{}),expectedProperty);
    }
}
