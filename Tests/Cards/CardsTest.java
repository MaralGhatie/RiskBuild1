package Cards;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

/**
 *
 * Test case for Card class
 * @author raoying
 * @version 3.0
 *
 */
public class CardsTest {

    Cards card = new Cards();

    @Test
    public void initializeCards() {

        card.initializeCards(0);

        Assert.assertEquals(card.getCardInfantry().get(0),0);
        Assert.assertEquals(card.getCardCavalry().get(0),0);
        Assert.assertEquals(card.getCardArtilery().get(0),0);
    }

    @Test
    public void exchangeCardforArmies() {
        card.initializeCards(0);
        card.exchangeCardForArmies(0,1);

        Assert.assertTrue(0 < card.getExchangedArmies(0));
    }

    @Test
    public void getExchanged_armies() {
        card.initializeCards(0);
        card.exchangeCardForArmies(0,1);

        Assert.assertTrue(card.getExchangedArmies(0) ==  5 );
    }

    @Test
    public void addInfantry() {

        card.initializeCards(0);
        card.addInfantry(0);

        Assert.assertEquals(card.getCardInfantry().get(0), 1);
    }

    @Test
    public void addCavalry() {
        card.initializeCards(0);
        card.addCavalry(0);
        card.addCavalry(0);

        Assert.assertEquals(card.getCardCavalry().get(0), 2);
    }

    @Test
    public void addArtillery() {
        card.initializeCards(0);
        card.addArtillery(0);
        card.addArtillery(0);
        card.addArtillery(0);

        Assert.assertEquals(card.getCardArtilery().get(0), 3);
    }

    @Test
    public void infantryExchangeReady() {

        card.initializeCards(0);

        card.addInfantry(0);
        card.addInfantry(0);
        card.addInfantry(0);

        Assert.assertTrue(card.infantryExchangeReady(0));
    }

    @Test
    public void cavalryExchangeReady() {
        card.initializeCards(0);

        card.addCavalry(0);
        card.addCavalry(0);
        card.addCavalry(0);

        Assert.assertTrue(card.cavalryExchangeReady(0));
    }

    @Test
    public void artileryExchangeReady() {
        card.initializeCards(0);

        card.addArtillery(0);
        card.addArtillery(0);
        card.addArtillery(0);

        Assert.assertTrue(card.artileryExchangeReady(0));

    }

    @Test
    public void comboExchangeReady() {

        card.initializeCards(0);

        card.addArtillery(0);
        card.addCavalry(0);
        card.addInfantry(0);

        Assert.assertTrue(card.comboExchangeReady(0));
    }

    @Test
    public void getTotal_cards_per_player() {

        card.initializeCards(0);

        card.addInfantry(0);
        card.addInfantry(0);
        card.addInfantry(0);
        card.addInfantry(0);
        card.addCavalry(0);
        card.addCavalry(0);
        card.addCavalry(0);

        Assert.assertEquals(card.getTotalCardsPerPlayer(0),7);
    }

}