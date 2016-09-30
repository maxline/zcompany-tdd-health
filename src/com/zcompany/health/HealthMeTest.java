package com.zcompany.health;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.zcompany.health.DayNorm.*;
import static com.zcompany.health.EActivity.*;
import static com.zcompany.health.HealthMe.ACTIVITIES_NUMBER;
import static org.junit.Assert.assertEquals;

public class HealthMeTest {

    private static final int DAYS_NUMBER = 7;

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

    private double countPercentDelta(int valuePlan, int valueFact) {
        return 100 * (valuePlan - valueFact) / valuePlan;
    }


    private void addReportLineDayLeft(HashSet<ReportLineDayLeft> report, EActivity eActivity, int valuePlan, int valueFact) {
        report.add(new ReportLineDayLeft(eActivity, valuePlan - valueFact));
    }

    @Test
    public void ReportDayLeftZeroPercentOfNormTest() {
        HashSet<ReportLineDayLeft> report = new HashSet<>(ACTIVITIES_NUMBER);

        addReportLineDayLeft(report, LIQUID, LIQUID_NORM, 0);
        addReportLineDayLeft(report, FOOD, FOOD_NORM, 0);
        addReportLineDayLeft(report, STEPS, STEPS_NORM, 0);

        assertEquals(report, healthMe.createReportDayLeft(TODAY));
    }


    @Test
    public void ReportDayLeftHundredPercentOfNormTest() {
        healthMe.takeActivity(TODAY, LIQUID, LIQUID_NORM);
        healthMe.takeActivity(TODAY, FOOD, FOOD_NORM);
        healthMe.takeActivity(TODAY, STEPS, STEPS_NORM);

        HashSet<ReportLineDayLeft> report = new HashSet<>(ACTIVITIES_NUMBER);

        addReportLineDayLeft(report, LIQUID, LIQUID_NORM, LIQUID_NORM);
        addReportLineDayLeft(report, FOOD, FOOD_NORM, FOOD_NORM);
        addReportLineDayLeft(report, STEPS, STEPS_NORM, STEPS_NORM);

        assertEquals(report, healthMe.createReportDayLeft(TODAY));
    }


    @Test
    public void ReportDayLeftTenPercentOfNormTest() {
        healthMe.takeActivity(TODAY, LIQUID, (int) (LIQUID_NORM * 0.1));
        healthMe.takeActivity(TODAY, FOOD, (int) (FOOD_NORM * 0.1));
        healthMe.takeActivity(TODAY, STEPS, (int) (STEPS_NORM * 0.1));

        HashSet<ReportLineDayLeft> report = new HashSet<>(ACTIVITIES_NUMBER);

        addReportLineDayLeft(report, LIQUID, LIQUID_NORM, (int) (LIQUID_NORM * 0.1));
        addReportLineDayLeft(report, FOOD, FOOD_NORM, (int) (FOOD_NORM * 0.1));
        addReportLineDayLeft(report, STEPS, STEPS_NORM, (int) (STEPS_NORM * 0.1));

        assertEquals(report, healthMe.createReportDayLeft(TODAY));
    }

    @Test
    public void ReportDayLeftThreeHundredPercentOfNormTest() {
        healthMe.takeActivity(TODAY, LIQUID, LIQUID_NORM * 3);
        healthMe.takeActivity(TODAY, FOOD, FOOD_NORM * 3);
        healthMe.takeActivity(TODAY, STEPS, STEPS_NORM * 3);

        HashSet<ReportLineDayLeft> report = new HashSet<>(ACTIVITIES_NUMBER);

        addReportLineDayLeft(report, LIQUID, LIQUID_NORM, LIQUID_NORM * 3);
        addReportLineDayLeft(report, FOOD, FOOD_NORM, FOOD_NORM * 3);
        addReportLineDayLeft(report, STEPS, STEPS_NORM, STEPS_NORM * 3);

        assertEquals(report, healthMe.createReportDayLeft(TODAY));
    }

    private void addReportLineDelta(HashSet<ReportLineDelta> report, EActivity eActivity, int valuePlan, Map<Integer, Double> activityDelta) {
        report.add(new ReportLineDelta(eActivity, activityDelta));
    }


    @Test
    public void ReportDeltaZeroPercentOfNormTest() {
        HashSet<ReportLineDelta> expectedReport = new HashSet<>(ACTIVITIES_NUMBER);

        Map<Integer, Double> zeroDelta = new HashMap<Integer, Double>() {{
            for (int day = 1; day <= DAYS_NUMBER; day++) {
                put(day, 1.0);
            }
        }};

        addReportLineDelta(expectedReport, LIQUID, LIQUID_NORM, zeroDelta);
        addReportLineDelta(expectedReport, FOOD, FOOD_NORM, zeroDelta);
        addReportLineDelta(expectedReport, STEPS, STEPS_NORM, zeroDelta);

        assertEquals(expectedReport, healthMe.createReportDelta());
    }


//    @Test
//    public void ReportDayDeltaHundredPercentOfNormTest() {
//        healthMe.takeActivity(TODAY, LIQUID, LIQUID_NORM);
//        healthMe.takeActivity(TODAY, FOOD, FOOD_NORM);
//        healthMe.takeActivity(TODAY, STEPS, STEPS_NORM);
//
//        HashSet<ReportLineDelta> report = new HashSet<>(ACTIVITIES_NUMBER);
//
//        addReportLineDelta(report, LIQUID, LIQUID_NORM, LIQUID_NORM);
//        addReportLineDelta(report, FOOD, FOOD_NORM, FOOD_NORM);
//        addReportLineDelta(report, STEPS, STEPS_NORM, STEPS_NORM);
//
//        assertEquals(report, healthMe.createReportDelta(TODAY));
//    }


//    @Test
//    public void ReportDayDeltaTenPercentOfNormTest() {
//        healthMe.takeActivity(TODAY, LIQUID, (int) (LIQUID_NORM * 0.1));
//        healthMe.takeActivity(TODAY, FOOD, (int) (FOOD_NORM * 0.1));
//        healthMe.takeActivity(TODAY, STEPS, (int) (STEPS_NORM * 0.1));
//
//        HashSet<ReportLineDelta> report = new HashSet<>(ACTIVITIES_NUMBER);
//
//        addReportLineDelta(report, LIQUID, LIQUID_NORM, (int) (LIQUID_NORM * 0.1));
//        addReportLineDelta(report, FOOD, FOOD_NORM, (int) (FOOD_NORM * 0.1));
//        addReportLineDelta(report, STEPS, STEPS_NORM, (int) (STEPS_NORM * 0.1));
//
//        assertEquals(report, healthMe.createReportDelta(TODAY));
//    }
//
//
//
//    @Test
//    public void dayReportThreeHundredPercentOfNormTest() {
//        healthMe.takeActivity(TODAY, LIQUID, LIQUID_NORM * 3);
//        healthMe.takeActivity(TODAY, FOOD, FOOD_NORM * 3);
//        healthMe.takeActivity(TODAY, STEPS, STEPS_NORM * 3);
//
//        HashSet<ReportLineDelta> report = new HashSet<>(ACTIVITIES_NUMBER);
//
//        addReportLineDelta(report, LIQUID, LIQUID_NORM, LIQUID_NORM * 3);
//        addReportLineDelta(report, FOOD, FOOD_NORM, FOOD_NORM * 3);
//        addReportLineDelta(report, STEPS, STEPS_NORM, STEPS_NORM * 3);
//
//        assertEquals(report, healthMe.createReportDelta(TODAY));
//    }



}
