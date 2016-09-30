package com.zcompany.health;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Mikhluk.
 */
public class HealthMeTest {
    private static final int LIQUID_NORM = 3000;
    private static final int FOOD_NORM = 2000;
    private static final int STEPS_NORM = 2000;

    private static final int GLASS_VOLUME = 200;
    private static final int BREAKFAST_VOLUME = 500;
    private static final int LUNCH_VOLUME = 700;
    private HealthMe healthMe;

    @Before
    public void setup() {
        healthMe = new HealthMe(LIQUID_NORM, FOOD_NORM, STEPS_NORM);
    }

    @Test
    public void iDrinkGlassWaterTest() {
        healthMe.drinkWater(GLASS_VOLUME);
        liquidTest(GLASS_VOLUME);
    }

    private void liquidTest(int liquidVolume) {
        int liquidLeft = healthMe.getLiquidLeft();
        assertEquals(LIQUID_NORM - liquidVolume, liquidLeft, 0.0);
    }

    private void foodTest(int food_Volume) {
        int foodLeft = healthMe.getFoodLeft();
        assertEquals(FOOD_NORM - food_Volume, foodLeft, 0.0);
    }

    @Test
    public void haveBreakfastTest() {
        healthMe.haveEating(GLASS_VOLUME, BREAKFAST_VOLUME);
        liquidTest(GLASS_VOLUME);
        foodTest(BREAKFAST_VOLUME);
    }

    @Test
    public void haveLunchTest() {
        healthMe.haveEating(GLASS_VOLUME, LUNCH_VOLUME);
        liquidTest(GLASS_VOLUME);
        foodTest(LUNCH_VOLUME);
    }

    @Test
    @Ignore
    public void dayTest(){
        healthMe.drinkWater(GLASS_VOLUME);
    }

}
