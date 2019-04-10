package View;

import Model.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * WorldDominationView displays:</br>
 * (1) the percentage of the map controlled by every player </br>
 * (2) the continents controlled by every player</br>
 * (3) the total number of armies owned by every player.</br>
 * (4) List of countries the player owns with armies inside each of them</br>
 * @author raoying
 * @version 2.0
 */
public class WorldDominationView implements Observer{

    ArrayList<Double> map_percentages = new ArrayList<Double>();
    ArrayList<ArrayList<Double>> player_continents = new ArrayList<ArrayList<Double>>();
    ArrayList<Integer> total_armies = new ArrayList<>();
    ArrayList<String> players = new ArrayList<>();
    
    /**
     * update method in a observer pattern. 
     * @param obs 
     * @param arg
     */
    public void update(Observable obs, Object arg) {

        map_percentages = ((Player) obs).getMapPercentages();
        player_continents = ((Player) obs).getPlayerContinents();
        total_armies = ((Player) obs).getTotalArmies();
        players = ((Player) obs).getPlayers();

        Player currentPlayer = ((Player) obs);
        int index = currentPlayer.getPlayerIndex();
        switch(currentPlayer.currentGamePhase) {
            case WORLD:
                playerWorldDomination(index);
                break;
        }
    }
    
    /**
     * This the World Domination View of current player,
     * showing the percentage of the map he/she has conquered, 
     * number of armies,
     * and continents owned by him/her.
     * @param playerindex
     * 
     */
    public void playerWorldDomination(int playerindex){
        //System.out.println("**** This the World Domination View for " + players.get(playerindex) + " ****");
        System.out.println();

        playerMapPercentage(playerindex);
        playerArmiesNum(playerindex);
        continentPercentages(playerindex);

    }
    
    /**
     * the percentage of the map the player has conquered
     * @param playerindex
     */
    public void playerMapPercentage(int playerindex){
        System.out.println("Player :  " + players.get(playerindex) + " "
                + map_percentages.get(playerindex) + "% of the whole map--");
        System.out.println();
    }
    
    /**
     * number of armies under the player
     * @param playerindex
     */
    public void playerArmiesNum(int playerindex){
        System.out.println("--The total number of armies you have is: " + total_armies.get(playerindex) + " --");
        System.out.println();
    }
    
    /**
     * percentage of each continent conquered by current player. 
     * @param index
     */
    public void continentPercentages(int index){

        System.out.println("Percentage of North America owned by you : " + player_continents.get(index).get(1) + "%");
        System.out.println("Percentage of South America owned by you : " + player_continents.get(index).get(2)+ "%");
        System.out.println("Percentage of Europe owned by you : " + player_continents.get(index).get(3)+ "%");
        System.out.println("Percentage of Africa owned by you : " + player_continents.get(index).get(4) + "%");
        System.out.println("Percentage of Asia owned by you : " + player_continents.get(index).get(0) + "%");
        System.out.println("Percentage of Australia owned by you : " + player_continents.get(index).get(5) + "%");
    }
    
    /**
     * call For First Domination
     * @param p_model
     */
    public void callForFirstDomination(Player p_model) {

        map_percentages = p_model.getMapPercentages();
        player_continents = p_model.getPlayerContinents();
        total_armies = p_model.getTotalArmies();
        players = p_model.getPlayers();
    }


/*
    public void playerCountriesandArmies (ArrayList<Integer> countries, ArrayList<Integer> armies){
        System.out.println("** List of countries you own with armies inside each of them **");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println("- " + countries.get(i) + " -> " + armies.get(i));
        }
        System.out.println();
    }
    */
}
