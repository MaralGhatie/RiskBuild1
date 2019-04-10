package Controller;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * test case for Misc class
 */
public class MiscTest {

    Misc msc = new Misc();

    @Test
    public void checkChoice() {

        boolean check = false;
        check = msc.checkChoice(1,1,6);

        Assert.assertTrue(check);

    }
}