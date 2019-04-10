package View;

import Model.Player;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

/**
 * This class will implement from observer
 * and it displays player's name and different phases.
 *
 * @author Niki
 * @version 2.0
 *
 */


public class PhaseView implements Observer {

    private TreeMap<Integer,Integer> country_owner = new TreeMap<>();
    private TreeMap<Integer,Integer> country_to_army = new TreeMap<>();
    private TreeMap<Integer,String> country_map = new TreeMap<>();
    private ArrayList<String> players = new ArrayList<>();


    /**
     * Because of implementing Observer, it is encessary to override this method.
     * This method is going to update different phases.
     * @param  obs an instance of Observable class (args0)
     * @param x an instance of Object class(args1)
     * @author Swetha
     * @version 2.0
     */

    public void update(Observable obs,Object x) {

        System.out.println("Systems updated !!!!!");

        country_to_army = ((Player) obs).getCountryToArmy();
        country_owner = ((Player) obs).getCountryOwner();
        country_map = ((Player) obs).getCountryMap();
        players = ((Player) obs).getPlayers();

        Player currentPlayer = ((Player) obs);
        int index = currentPlayer.getPlayerIndex();
        switch(currentPlayer.currentGamePhase) {
            case REINFORCEMENT:
                reinforcementPhaseView(currentPlayer.getPlayer(index));
                break;
            case ATTACK:
                reinforcementPhaseView2();
                clearView();
                attackPhaseView(currentPlayer.getPlayer(index));
                break;
            case FORTIFICATION:
                attackPhaseView2();
                clearView();
                fortifyPhaseView(currentPlayer.getPlayer(index));
                break;
            case ENDTURN:
                fortifyPhaseView2(currentPlayer.getPlayer(index));
                clearView();
                break;
        }
    }

    /**
     * This method is going to print player's name and goes to reinforcement phase.
     * @param player
     */

    public void reinforcementPhaseView(String player) {
        System.out.println("");
        System.out.println("\n****************** "
                + player
                + " REINFORCEMENT PHASE BEGINS ************************\n");
    }
    /**
     * This method is going to print armies after reinforcement phase.
     */
    public void reinforcementPhaseView2(){

        System.out.println("");
        System.out.println("----------------Armies After Reinforcement---------------");

        for (int key : country_to_army.keySet()){
            System.out.println(key + ". "+ country_map.get(key) + "(" +  country_to_army.get(key) + ")");
        }
    }
    /**
     * This method is going to print player's name before going to attack phase.
     * @param player
     */
    public void attackPhaseView(String player) {
        System.out.println("");
        System.out.println("\n****************** "
                + player
                + " ATTACK PHASE BEGINS ************************\n");

    }

    /**
     * This method is going to print armies after attack phase.
     */
    public void attackPhaseView2(){
        System.out.println("");
        System.out.println("----------------Armies after Attack-----------------------");

        for (int key : country_owner.keySet()){

            System.out.println("Country number = " + key +
                    "\t\tName => " + country_map.get(key) +
                    "\t\tArmies => " + country_to_army.get(key) +
                    "\t\tOwned by => " + players.get(country_owner.get(key)));
        }
    }

    /**
     * This method is going to print player's name and goes to fortification phase.
     * @param player
     */
    public void fortifyPhaseView(String player) {
        System.out.println("");
        System.out.println("\n****************** "
                + player
                +" FORTIFICATION PHASE BEGINS ************************\n");
    }


    /**
     * This method is going to print armies after fortify phase.
     */
    public void fortifyPhaseView2(String player) {
        System.out.println("");
        System.out.println("\n....................... "
                + player
                +"'s TURN IS OVER ......................\n");
    }
    
    /**
     * This function is for design.
     */
    public void clearView(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}

