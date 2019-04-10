package Model;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Test cases for saving/loading a game
 * @author raoying
 * @version 3.0
 *
 *
 */
public class SaveLoad {

    static private int file_counter = 0;

    @Test
    public void saveTest(){
        String filepath = ".//saves/";
        String filename = "Save_state" + Integer.toString(file_counter) + ".txt";
        //System.out.println(filename);
        Assert.assertNotNull(filename);
    }

    @Test
    public  void loadTest(){
        String filepath = ".//saves/";
        String filename = "Save_state" + Integer.toString(file_counter) + ".txt";

        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line1 = br.readLine();
            String line2 = br.readLine();
            int num = Integer.parseInt(line1);
            Assert.assertNotNull(line1);
            Assert.assertTrue(line2.equalsIgnoreCase("fortification") ||
                    line2.equalsIgnoreCase("reinforcement") ||
                    line2.equalsIgnoreCase("attack"));
        }
        catch (Exception e){

        }
    }
}