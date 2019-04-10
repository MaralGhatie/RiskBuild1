package Model;

import Controller.Control;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class Reinforce {
    @Test
    public void reinforce() throws Exception {

        Setup set = new Setup();
        Control ctrl = new Control();
        Player p_model = new Player();

        set.loadMap();

        //p_model.initialize(set.getCountryMap(),set.getAdjMap(),set.getNumCountry());

        ctrl.initializeCards(0);
        ctrl.initializeCards(1);

        ArrayList<String> players = new ArrayList<>();
        players.add("paras");
        players.add("varun");

        ArrayList <Integer> strategies = new ArrayList<Integer>();
        strategies.add(2);
        strategies.add(3);

        //p_model.setPlayers(2,players ,strategies);
        p_model.simulateInitial();

        ArrayList<Integer> pcfor0 = p_model.getPlayerCountries(0);
        ArrayList<Integer> cafor0 = p_model.getCountryArmy(0);

        int prevarmy = p_model.getArmiesAtCountry(pcfor0.get(0));

        p_model.doreinforce(0,pcfor0.get(0),4,2);

        int afterarmy = p_model.getArmiesAtCountry(pcfor0.get(0));

        Assert.assertTrue(afterarmy-2 == prevarmy );

    }
}