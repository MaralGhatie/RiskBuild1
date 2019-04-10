package View;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * MainView creates a welcome page object when user enters the game,
 * @author raoying
 * @version 2.0
 */
public class MainView {
    Scanner read = new Scanner(System.in);

    /**
     * This method is only for start up phase view
     */

    public void startUpPhaseView(){
        System.out.println("\n****************** "
                + " STARTUP PHASE ************************\n");
        System.out.println();
    }
    /**
     * startView provides the welcome page where user input their choice: </br>
     *  1. Play Game </br> 2. Edit Map </br>3. Quit Game </br>
     * also it verifies validation of choice input by user.
     * @return choice int user's choice.
     */
    public int startView(){

        System.out.println("\t\t\t Hello .... Welcome to Risk!");
        System.out.println("\n\n\n\t\t Choose one of the following options");
        System.out.println("1. Play Game \n2. Edit Map \n3. Quit Game \n4. Tournament Mode \n5. Load a previous game" );

        int choice = -1;
        boolean flag = false;

        do {
            choice = read.nextInt();
            if (choice<6 && choice>0){
                System.out.println("You entered choice "+ choice);
                flag = true;

            } else
                System.out.println("Please enter correct choice");
        }while(!flag);

        return choice;
    }
    /**
     * editView runs when user input their choice of "2. Edit Map"
     * users can either create their own map from scratch or load a map from existing file.
     * also it verifies validation of choice input by user.
     * @return choice int user's choice.
     */
    public int editView(){
        System.out.println("1. Make a new map \n2. Load existing map");
        boolean flag = false;
        int choice = -1;
        do {
            choice = read.nextInt();
            if(choice == 1){
                System.out.println("--Making a map from scratch--");
                flag = true;
            }
            else if (choice == 2){
                System.out.println("--Load existing maps--");
                flag = true;

            } else
                System.out.println("Please enter correct choice");
        }while(!flag);


        return choice;
    }
    /**
     * quitView runs when user choose "3. Quit Game"
     */
    public void quitView(){
        System.out.println("Thanks for playing...See you...NEVER !!!!");
    }
    /**
     * numOfPlayerView enables users to choose the number of players(2-4)
     * @return choice int user's choice.
     */
    public int numOfPlayerView(){

        System.out.println("Choose How many players to play (2-4)");
        boolean flag = true;
        int choice = read.nextInt();
        while(flag){
            if(choice > 1 && choice < 5){
                flag = false;
            }
            else{
                System.out.println("Enter correct choice");
            }
        }

        return choice;
    }

    public String playerNameView(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter player's name: ");
        String name = sc.nextLine();


        return name;
    }

    /**
     * This method is for game over
     */

    public void gameOverView(String name){
        System.out.println();
        System.out.println("\n****************** "
                + " GAME OVER ************************\n");
        System.out.println();
        System.out.println();
        System.out.println("\n****************** YEEEESSS!!! "
                + name + " WON THE GAME ************************\n");
        System.out.println();
    }

    public String loadGameView(){

        File folder = new File(".//saves/");
        File[] list = folder.listFiles();
        String savefile = "NO FILE";

        if(list.length != 0){
            System.out.println("The files in the directory are:");
            for (int i = 0; i < list.length; i++) {
                System.out.println("File " + Integer.toString(i+1) + " ===> " +  list[i].getName());
            }

            System.out.println("Enter the File # that you want to read");

            Scanner scan = new Scanner(System.in);
            int choice = scan.nextInt();

            savefile = list[choice-1].getName();

            System.out.println("Chosen saved file is = " + savefile);
        }

        else{
            System.out.println("No saved game has been found");
        }


        return savefile;
    }

    public void turnView(){
        System.out.println("Entering X player turn");
    }
    public void endTurnView(){
        System.out.println("Do you want to countinue? ");
    }

    public int strategyView() {

        Scanner read =  new Scanner(System.in);
        System.out.println("Enter the player Strategy for this player ...");
        System.out.println("1> Human\n2> Aggressive\n3> Benevolent\n4> Random\n5> Cheater");

        int strategyselected = read.nextInt();

        return strategyselected;

    }
    
    public void tournamentView(){

        System.out.println("\n-------------------- "
                + " Welcome to Tournament --------------\n");
        System.out.println();

    }
}
