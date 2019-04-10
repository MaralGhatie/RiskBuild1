package View;

import Cards.Cards;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * CardExchangeView is implemented as the concrete observer in an Observer pattern.</n>
 * It's created only during the reinforcement phase,
 * and cease to exist after the cards exchange.</n> 
 * @author raoying
 * @version 2.0 
 */
public class CardExchangeView implements Observer {

    ArrayList<Integer> cards_infantry = new ArrayList<>();
    ArrayList<Integer> cards_cavalry = new ArrayList<>();
    ArrayList<Integer> cards_artilery = new ArrayList<>();

    Cards object;
    /**
     * update method updates the cardExchangevView when cards owned by the player change.  
     */
    public void update(Observable obs, Object arg) {

         cards_infantry = ((Cards) obs).getCardInfantry();
         cards_cavalry = ((Cards) obs).getCardCavalry();
         cards_artilery =((Cards) obs).getCardArtilery();

         object = ((Cards) obs);
    }
    /**
     * cardExchangevView method displays all the cards owned by the current player, 
     * @param index index of current player.
     */
    public void cardExchangevView(int index){

        System.out.println("Lets exchange some cards .... Shall we ??");

        System.out.println("Following are list of cards that you own : ");
        System.out.println("Infantry cards => " + cards_infantry.get(index));
        System.out.println("Cavalry cards => " + cards_infantry.get(index));
        System.out.println("Artilery cards => " + cards_infantry.get(index));

        System.out.println("");

        System.out.println("Following are the available options to you :");
    }
}
