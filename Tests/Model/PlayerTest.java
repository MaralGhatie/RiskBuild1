
package Model;
import Controller.*;

//import Controller.Control;
import org.junit.Assert;
import org.junit.Test;
//import sun.reflect.generics.tree.Tree;

//import javax.transaction.TransactionRequiredException;
import java.util.*;

/**
 * Test cases for player class
 */
public class PlayerTest {
    static private int file_counter = 0;
/*
    @Test
    public void attack() throws Exception {

        Setup set = new Setup();
        Control ctrl = new Control();
        Player p_model = new Player();

        set.loadMap();

        p_model.initialize(set.getCountryMap(),set.getAdjMap(),set.getNumCountry());

        ctrl.initializeCards(0);
        ctrl.initializeCards(1);

        ArrayList<String> players = new ArrayList<>();
        players.add("paras");
        players.add("varun");

        p_model.setPlayers(2,players);
        p_model.simulateInitial();
        p_model.printPlayerArmies();
        p_model.printPlayerCountries();

        ArrayList<Integer> pcfor0 = p_model.getPlayerCountries(0);
        ArrayList<Integer> pcfor1 = p_model.getPlayerCountries(1);

        TreeMap<Integer,Integer> catoa = p_model.getCountryToArmy();
        TreeMap<Integer,Integer> catowoner = p_model.getCountryOwner();

        ArrayList<Integer> cafor0 = p_model.getCountryArmy(0);
        ArrayList<Integer> cafor1 = p_model.getCountryArmy(1);

        int attacker = 0;int atarmy;
        int defender = 0;int defarmy;

        for (int i = 0;i < cafor0.size() ;i++){
            if (cafor0.get(i) > 0){
                attacker = pcfor0.get(i);
                break;
            }
        }

        defender = pcfor1.get(0);

        System.out.println("Attacker armies are = " + p_model.getArmiesAtCountry(attacker));
        System.out.println("Defender armies are = " + p_model.getArmiesAtCountry(defender));

        int prevarmy = p_model.getArmiesAtCountry(attacker);

        p_model.doAttackAllOut(attacker,defender);

        TreeMap<Integer,Integer> catoanew = p_model.getCountryToArmy();

        int nowarmy = p_model.getArmiesAtCountry(attacker);

        System.out.println("previous armies = "+ prevarmy);
        System.out.println("New armies = " + nowarmy);

        Assert.assertTrue(prevarmy != nowarmy);

        Random rand = new Random();

        for (int i = 0;i < players.size(); i++){

            ArrayList<Integer> countrynums = new ArrayList<Integer>();
            countrynums.addAll(country_to_army.keySet());

            int armiesgiven = 10;
            while (armiesgiven > 0)
            {
                int position = rand.nextInt(countrynums.size());
                int chosencountry = countrynums.get(position);
                int choosearmies = rand.nextInt(7) + 1;

                country_to_army.replace(chosencountry,country_to_army.get(chosencountry)+choosearmies);

                armiesgiven = armiesgiven - choosearmies;
            }
        }






    }*/

@Test
    public void saveGameTest(){
        String filepath = ".//saves/";
        String filename = "Save_state" + Integer.toString(file_counter) + ".txt";
        //System.out.println(filename);
        Assert.assertNotNull(filename);

    }


}
