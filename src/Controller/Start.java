package Controller;



import java.io.IOException;
import java.util.ArrayList;

import com.sun.javafx.iio.common.SmoothMinifier;

import Map.Loadexmap;
import Map.Mapper;
import Model.Player;
import Model.Setup;
import View.MainView;
import View.PhaseView;
import View.WorldDominationView;
import sun.applet.Main;
import sun.net.www.content.text.plain;

/**
 * This class is one of our controller classes. It's the one which have the main method.
 * @author Niki
 * @version 2.0
 */

public class Start {

    /**
     * This is the main function.
     * It will creates objects from Control class as a controller and from MainView as the view. For each choice of
     * the player, it will call a specific method.
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {

        Control control = new Control();
        Load load = new Load();
        Tournament tournmnt = new Tournament();
        MainView mview = new MainView();
        Setup smodel = new Setup();

        int mchoice = mview.startView();

        if (mchoice == 1){
            playNewGame(mview,smodel,control);
        }
        if (mchoice == 2){

            int choice = mview.editView();
            //choice = mview.editView();

            if (choice == 1) {
                makeNewMap(mview,smodel,control);
            }
            if (choice == 2) {
                loadExistingMap(mview,smodel,control);
            }
        }

        if (mchoice == 3){
            quitGame();
        }
        if (mchoice == 4){
            playTournament(mview, tournmnt);
           
            //trc.startTournament();
        }

        if (mchoice == 5){
            loadGame(mview,load);
        }

    }

    /**
     * This function starts a new game.
     * @param mview an object of the MainView
     * @param smodel an object of Setup
     * @param control an object Control
     * @throws Exception
     */

    public static void playNewGame(MainView mview, Setup smodel, Control control) throws Exception {

        smodel = new Setup();
        smodel.loadMap();
        control.setPlay(smodel);
    }

    /**
     * Player creates a new map by this method.
     * @param mview an object of the MainView
     * @param smodel an object of Setup
     * @param control an object of Control
     */

    public static void makeNewMap(MainView mview, Setup smodel, Control control){

        Mapper newmap = new Mapper();
        newmap.makeNew();
        smodel.setEverything(newmap.getNewMap());
        control.setPlay(smodel);
    }

    /**
     * Player uses an existing map by this method.
     * @param mview an object of the MainView
     * @param smodel an object of Setup
     * @param control an object of Control
     */

    public static void loadExistingMap(MainView mview, Setup smodel, Control control) {
        Loadexmap exmap = new Loadexmap();
        try {
            boolean result = exmap.makeMap();
            if (result) {
                smodel.setEverything(exmap.getAdjmap());
                control.setPlay(smodel);
            } else {
                System.out.println("Sorry your loaded map was not correct.... So we are exiting game !!");
                //m_view.quitView();
                System.exit(0);
            }
        } catch (Exception e) {}
    }

    /**
     * This method is for exiting the game.
     */

    public static void quitGame(){
        MainView mview = new MainView();
        mview.quitView();
    }

    public static void loadGame(MainView mview,Load load){

        String savefilename = mview.loadGameView();
        if(!savefilename.equalsIgnoreCase("NO FILE")){
            try {
                load.loadState(savefilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.exit(0);
        }

    }
    
    public static void playTournament(MainView mview, Tournament trc){
        mview.tournamentView();
        trc.setall();
    }
}




