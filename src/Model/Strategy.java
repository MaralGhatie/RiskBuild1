package Model;

public interface Strategy {

    public void sReinforce(int playerIndex, int extraarmies);
    /**
     * Fortification phase, whose implementation will
     * vary depending on the strategy.
     *
     */
    public void sFortify(int playerIndex);

    public void sAttack(int playerIndex);
}
