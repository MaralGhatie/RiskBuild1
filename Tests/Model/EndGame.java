package Model;

import Controller.Control;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Test cases for ending a game normally.
 * @author raoying
 * @version 3.0
 *
 *
 */
public class EndGame {

    @Test
    public void endGameTest() throws Exception {
        Setup set = new Setup();
        Control ctrl = new Control();
        Player p_model = new Player();

        set.loadMap();

        //p_model.initialize(set.getCountryMap(),set.getAdjMap(),set.getNumCountry());

        ctrl.initializeCards(0);
        ctrl.initializeCards(1);

        ArrayList<String> players = new ArrayList<>();
        players.add("abc");
        players.add("def");

        ArrayList<Integer> strategies = new ArrayList<Integer>();
        strategies.add(2);
        strategies.add(3);

       // p_model.setPlayers(2,players , strategies);



        Set<Integer> pcfor =  set.getCountryMap().keySet();
        ArrayList<Integer> pcfor0 = new ArrayList<Integer>();
        for (int i : pcfor){
            int c = pcfor.iterator().next();
            pcfor0.add(c);
        }
        ArrayList<Integer> pcfor1 = new ArrayList<Integer>();

        boolean gameoverf = false;
        if (pcfor0.size() == 36){
            gameoverf = true;
        }
        if (pcfor1.size()==0){
            p_model.checkforkick();
        }

        Assert.assertEquals(36,pcfor.size());
        Assert.assertTrue(gameoverf);
        //Assert.assertEquals(0 , p_model.getPlayers().size());
    }
}