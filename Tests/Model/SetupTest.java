package Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

/**
 * Validation of a correct setup phase.
 * @author raoying
 * @version 3.0
 *
 */
public class SetupTest {

    Setup s_model = new Setup();

    @Before
    public void beforeAllTestCases() throws Exception {

        s_model.loadMap();
    }

    @Test
    public void countriesDivisible() {

        boolean check = s_model.countriesDivisible(5);
        Assert.assertFalse(check);
    }

    @Test
    public void getNumCountry() {

        int numcountries = s_model.getNumCountry();
        Assert.assertEquals(36,numcountries);
    }

    @Test
    public void getCountryMap() {

        Assert.assertNotNull(s_model.getCountryMap());
    }

    @Test
    public void getAdjMap() {
        Assert.assertNotNull(s_model.getAdjMap());
    }

    @Test
    public void getAsiaCountries() {
        s_model.continentControl();
        Assert.assertEquals(s_model.getAsiaCountries().size(),8);
    }

    @Test
    public void getNamericaCountries() {
        s_model.continentControl();
        Assert.assertEquals(s_model.getNamericaCountries().size(),3);
    }

    @Test
    public void getSamericaCountries() {
        s_model.continentControl();
        Assert.assertEquals(s_model.getSamericaCountries().size(),5);
    }

    @Test
    public void getEuropeCountries() {
        s_model.continentControl();
        Assert.assertEquals(s_model.getEuropeCountries().size(),10);
    }

    @Test
    public void getAfricaCountries() {
        s_model.continentControl();
        Assert.assertEquals(s_model.getAfricaCountries().size(),7);
    }

    @Test
    public void getAustraliaCountries() {
        s_model.continentControl();
        Assert.assertEquals(s_model.getAustraliaCountries().size(),3);
    }
}