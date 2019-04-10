package TestSuit;

import Cards.CardsTest;
import Controller.ControlTest;
import Controller.DiceTest;
import Controller.Misc;
import Controller.MiscTest;
import Map.CheckMap;
import Model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import testMap.CheckMapTest;
import Model.SaveLoad;
import Model.EndGame;
import Controller.StartupPhase;

@RunWith(Suite.class)
@Suite.SuiteClasses({StartupPhase.class, CardsTest.class, DiceTest.class, ControlTest.class, MiscTest.class, SetupTest.class, SaveLoad.class, EndGame.class})
public class TestSuit {
}