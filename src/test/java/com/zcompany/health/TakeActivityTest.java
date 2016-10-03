package com.zcompany.health;

import org.junit.Before;
import org.junit.Test;

import static com.zcompany.health.DayNorm.*;
import static com.zcompany.health.EActivity.*;
import static org.junit.Assert.assertEquals;

public class TakeActivityTest {

    private static final int GLASS_VOLUME = 200;
    private static final int BREAKFAST_VOLUME = 500;
    private static final int LUNCH_VOLUME = 700;
    private static final int SHORT_WALK_STEPS = 500;
    private static final int TODAY = 1;
    private HealthMe healthMe;

    @Before
    public void setup() {
        healthMe = new HealthMe(LIQUID_NORM, FOOD_NORM, STEPS_NORM);
    }

    private void liquidTest(int liquidVolume) {
        assertEquals(LIQUID_NORM - liquidVolume, healthMe.getActivityLeft(TODAY, LIQUID));
    }

    @Test
    public void drinkGlassWaterTest() {
        healthMe.takeActivity(TODAY, LIQUID, GLASS_VOLUME);
        liquidTest(GLASS_VOLUME);
    }

    private void foodTest(int food_Volume) {
        assertEquals(FOOD_NORM - food_Volume, healthMe.getActivityLeft(TODAY, FOOD));
    }

    @Test
    public void takeFoodTest() {
        healthMe.takeActivity(TODAY, FOOD, BREAKFAST_VOLUME);
        foodTest(BREAKFAST_VOLUME);
    }

    @Test
    public void takeWalkTest() {
        healthMe.takeActivity(TODAY, STEPS, SHORT_WALK_STEPS);
        assertEquals(STEPS_NORM - SHORT_WALK_STEPS, healthMe.getActivityLeft(TODAY, STEPS));
    }

    @Test
    public void takeBreakfastTest() {
        healthMe.takeEating(TODAY, GLASS_VOLUME, BREAKFAST_VOLUME);
        liquidTest(GLASS_VOLUME);
        foodTest(BREAKFAST_VOLUME);
    }

    @Test
    public void takeLunchTest() {
        healthMe.takeEating(TODAY, GLASS_VOLUME, LUNCH_VOLUME);
        liquidTest(GLASS_VOLUME);
        foodTest(LUNCH_VOLUME);
    }
}
