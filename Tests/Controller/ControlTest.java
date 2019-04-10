

package Controller;

import Model.Player;
import Model.Setup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test cases for control class.
 */
public class ControlTest {
    Control control = new Control();
    ArrayList<String> temp_player = new ArrayList<>();

    Player p_model = new Player();

    Setup s_model = new Setup();

    @BeforeEach
    public void setuUP() throws Exception {
        s_model.loadMap();
       // p_model.initialize(s_model.getCountryMap(), s_model.getAdjMap(), s_model.getNumCountry());

        ArrayList <Integer> strategies = new ArrayList<Integer>();
        strategies.add(2);
        strategies.add(3);

        control.addPlayerName("paras",2);
        control.addPlayerName("varun",3);
      //  p_model.setPlayers(2,control.getPlayers(),strategies);
        //p_model.print_ctoamap();
        p_model.simulateInitial();
    }

    @Test
    public void addPlayerName() {
    }

    @Test
    public void testPlayerName() {



        control.addPlayerName("Paras",2);
        control.addPlayerName("Varun",3);

        temp_player = control.getPlayers();

        for (int i = 0;i < temp_player.size();i++){
            Assert.assertNotNull(temp_player.get(i));
        }
    }

    @Test
    public void testPlayerSize() {

        temp_player = control.getPlayers();

        Assert.assertEquals(2,temp_player.size());
    }

}