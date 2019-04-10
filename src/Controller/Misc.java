package Controller;

/**
 * This is one of our controllers. It checks the choices whether they are valid or not.
 * @author Niki
 * @version 2.0
 */

public class Misc {

    /**
     * This method checks tghe validity of the choice of the player.
     * @param choice player's choice
     * @param flr minimum for the choice number
     * @param ceil maximum for the choice number
     * @return boolean true if the choice were entered right
     */

    public boolean checkChoice(int choice,int flr,int ceil) {

        if (choice <= ceil & choice >= flr) {
            return true;
        } else {
            System.out.println("ERROR !!!... Enter Again");
            return false;
        }
    }
}
