package Controller;

import Model.Player;
import Model.Setup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Random;

/**
 * Validation of a correct startup phase
 * @author raoying
 * @version 3.0
 */
public class StartupPhase {

    @Test
    public void startUpPhaseTest() throws Exception {
        Setup set = new Setup();
        Control ctrl = new Control();
        Player p_model = new Player();

        set.loadMap();

       // p_model.initialize(set.getCountryMap(),set.getAdjMap(),set.getNumCountry());

        ctrl.initializeCards(0);
        ctrl.initializeCards(1);
        // ctrl.initializeCards(2);
        //  ctrl.initializeCards(3);

        ArrayList<String> players = new ArrayList<>();
        players.add("abc");
        players.add("def");
        // players.add("ghi");
        // players.add("jkl");

        ArrayList<Integer> strategies = new ArrayList<Integer>();
        strategies.add(2);
        strategies.add(3);
        // strategies.add(4);
        // strategies.add(5);


        //p_model.setPlayers(2,players , strategies);

        int initialarmies = 5;
        Random random = new Random();

        ArrayList<Integer> pcfor0 = p_model.getPlayerCountries(0);
        ArrayList<Integer> cafor0 = p_model.getCountryArmy(0);

        int prevarmy = p_model.getArmiesAtCountry(pcfor0.get(0));

        // int afterarmy = p_model.getArmiesAtCountry(pcfor0.get(0));

        for (int j=0 ; j<5 ; j++){
            cafor0.add(random.nextInt(17),1);
        }

        int sumofarmies = 0;
        for (int i=0 ; i<cafor0.size() ; i++){
            sumofarmies += cafor0.get(i);
        }
        Assert.assertTrue(sumofarmies == 5);
        Assert.assertEquals(18,pcfor0.size());
    }


}