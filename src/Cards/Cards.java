package Cards;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * This class has instances and functions of cards.
 * @author Niki
 * @version 2.0
 */

public class Cards extends Observable {

    static ArrayList<Integer> total_cards_per_player = new ArrayList<>();

    static ArrayList<Integer> exchanged_armies = new ArrayList<>();

    static ArrayList <Integer> card_infantry = new ArrayList <Integer>() ;
    static ArrayList <Integer> card_cavalry = new ArrayList <Integer>() ;
    static ArrayList <Integer> card_artilery = new ArrayList <Integer>() ;

    /**
     * This method creates arrays for each card for all the players. When a player loses his/her cards, another player
     * will get these cards. It follows an observer pattern and whenever a change happens on cards, it will notify
     * the players (as an observable class).
     * @param index it is the player index.
     * @author Niki
     * @version 2.0
     */

    public void initializeCards(int index){
        card_infantry.add(index,0);
        card_cavalry.add(index,0);
        card_artilery.add(index,0);

        exchanged_armies.add(index,0);

        setChanged();
        notifyObservers(this);
    }

    /**
     * This method is for exchanging the cards to the armies.
     * @param index it is the player index.
     * @param option it is entered by scanner.
     * @author Niki
     * @version 2.0
     */

    public void exchangeCardForArmies(int index,int option){

        switch (option){

            case 1:
                card_infantry.set(index,card_infantry.get(index) - 3);
                exchanged_armies.set(index,5);
                System.out.println("Congrats...you have been awarded 5 armies !!!");
                break;

            case 2:
                card_cavalry.set(index,card_cavalry.get(index) - 3);
                exchanged_armies.set(index,5);
                System.out.println("Congrats...you have been awarded 5 armies !!!");
                break;

            case 3:
                card_artilery.set(index,card_artilery.get(index) - 3);
                exchanged_armies.set(index,5);
                System.out.println("Congrats...you have been awarded 5 armies !!!");
                break;

            case 4:
                card_infantry.set(index,card_infantry.get(index) - 1);
                card_cavalry.set(index,card_cavalry.get(index) - 1);
                card_artilery.set(index,card_artilery.get(index) - 1);

                exchanged_armies.set(index,5);
                System.out.println("Congrats...you have been awarded 5 armies !!!");
                break;

            case 5:
                System.out.println("Let's move on !!!");
                break;
        }
    }

    /**
     * This method gives the player his/her armies to use them.
     * @param index it is the player index.
     * @return armiesawarded which is extra armies for each player.
     * @author Niki
     * @version 2.0
     */

    public static Integer getExchangedArmies(int index) {
        int armiesawarded = exchanged_armies.get(index);
        exchanged_armies.set(index,0);

        return armiesawarded;
    }

    /**
     * This method is used when a player gets an infantry card randomly and It will be added to its array.
     * @param index it is the player index.
     */

    public void addInfantry(int index){
        card_infantry.set(index,card_infantry.get(index) + 1);
        System.out.println("You have been awarded a card....wow !!");
    }

    /**
     * This method is used when a player gets an cavalry card randomly and It will be added to its array.
     * @param index it is the player index.
     * @author Niki
     * @version 2.0
     */

    public void addCavalry(int index){
        card_cavalry.set(index,card_cavalry.get(index) + 1);
        System.out.println("You have been awarded a card....wow !!");
    }

    /**
     * This method is used when a player gets an artillery card randomly and It will be added to its array.
     * @param index it is the player index.
     * @author Niki
     * @version 2.0
     */

    public void addArtillery(int index){
        card_artilery.set(index,card_artilery.get(index) + 1);
        System.out.println("You have been awarded a card....wow !!");
    }

    /**
     * This method creates a random card after a player wins at least one country.
     * This method follows the observer pattern. When sth happens, it will be shown to the player which is
     * an observable class.
     * @param index it is the player index.
     * @author Niki
     * @version 2.0
     */

    public void addRandomCard(int index){

        Random rand = new Random();
        int cardchoice = rand.nextInt(3);

        switch (cardchoice){
            case 0:
                addInfantry(index);
                break;
            case 1:
                addCavalry(index);
                break;
            case 2:
                addArtillery(index);
                break;
        }

        setChanged();
        notifyObservers(this);
    }

    /**
     * This method checks whether if the player has at least 3 infantry cards.
     * @param index it is the player index.
     * @return boolean if it is true, the player can exchange the cards.
     * @author Niki
     * @version 2.0
     */

    public boolean infantryExchangeReady(int index){

        if (card_infantry.get(index) > 2)
            return true;
        else
            return false;
    }

    /**
     * This method checks whether if the player has at least 3 cavalry cards.
     * @param index it is the player index.
     * @return boolean if it is true, the player can exchange the cards.
     * @author Niki
     * @version 2.0
     */

    public boolean cavalryExchangeReady(int index){

        if (card_cavalry.get(index) > 2)
            return true;
        else
            return false;
    }

    /**
     * This method checks whether if the player has at least 3 artillery cards.
     * @param index it is the player index.
     * @return boolean if it is true, the player can exchange the cards.
     * @author Niki
     * @version 2.0
     */

    public boolean artileryExchangeReady(int index){

        if (card_artilery.get(index) > 2)
            return true;
        else
            return false;
    }

    /**
     * This method checks whether if the player has at least one card of each kind.
     * @param index it is the player index.
     * @return boolean if it is true, the player can exchange the cards.
     * @author Niki
     * @version 2.0
     */

    public boolean comboExchangeReady(int index){

        if (card_infantry.get(index) > 0 && (card_cavalry.get(index) > 0 && card_artilery.get(index)> 0))
            return true;
        else
            return false;
    }

    /**
     * This method checks that what options the player has for exchanging.
     * @param index it is the player index.
     * @return arraylist
     * @author Niki
     * @version 2.0
     */

    public ArrayList<Integer> availableChoices(int index){

        ArrayList<Integer> availablechoices = new ArrayList<>();

        if (infantryExchangeReady(index)){
            System.out.println("Option => 1   Exhchange 3 Infantry Cards for 5 armies  ");
            availablechoices.add(1);
        }else {
            System.out.println("Option => 1   You can't choose this option because you don't have 3 infantry cards");
        }

        if (cavalryExchangeReady(index)){
            System.out.println("Option => 2   Exhchange 3 Cavalry Cards for 5 armies  ");
            availablechoices.add(2);
        }else {
            System.out.println("Option => 2   You can't choose this option because you don't have 3 cavalry cards");
        }

        if (artileryExchangeReady(index)){
            System.out.println("Option => 3   Exhchange 3 Artilery Cards for 5 armies  ");
            availablechoices.add(3);
        }else {
            System.out.println("Option => 3   You can't choose this option because you don't have 3 artilery cards");
        }

        if (comboExchangeReady(index)){
            System.out.println("Option => 4   Exhchange a combo of Cards(1 of each) for 5 armies  ");
            availablechoices.add(4);
        }else {
            System.out.println("Option => 4   You can't choose this option because you don't have a combo(1 of each)");
        }


        return availablechoices;
    }

    /**
     * This method shows the number of each cards that the player has.
     * @param index it is the player index.
     * @return totalcards
     * @author Niki
     * @version 2.0
     */

    public static int getTotalCardsPerPlayer(int index) {
        int totalcards = card_infantry.get(index) + card_cavalry.get(index) + card_artilery.get(index);
        return totalcards;
    }

    /**
     * This is a getter method.
     * @return card_infantry
     */

    public ArrayList getCardInfantry(){
        return card_infantry;
    }

    /**
     * This is a getter method.
     * @return card_cavalry
     */

    public ArrayList getCardCavalry(){
        return card_cavalry;
    }

    /**
     * This is a getter method.
     * @return card_artilery
     */

    public ArrayList getCardArtilery(){
        return card_artilery;
    }
}

