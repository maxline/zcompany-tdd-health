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

public class WeekReportTest {

    private static final int DAYS_IN_WEEK = 7;
    private HealthMe healthMe;

    @Before
    public void setup() {
        healthMe = new HealthMe(LIQUID_NORM, FOOD_NORM, STEPS_NORM);
    }


//    private double countPercentDelta(int valuePlan, int valueFact) {
//        return 100 * (valuePlan - valueFact) / valuePlan;
//    }


    private void addReportLineDelta(HashSet<ReportLineDelta> report, EActivity eActivity, int valuePlan, Map<Integer, Double> activityDelta) {
        report.add(new ReportLineDelta(eActivity, activityDelta));
    }

    @Test
    public void WeekReportDeltaZeroPercentTest() {
        HashSet<ReportLineDelta> expectedReport = new HashSet<>(ACTIVITIES_NUMBER);

        Map<Integer, Double> oneHundredDelta = new HashMap<Integer, Double>() {{
            for (int day = 1; day <= DAYS_IN_WEEK; day++) {
                put(day, 100.0);
            }
        }};

        addReportLineDelta(expectedReport, LIQUID, LIQUID_NORM, oneHundredDelta);
        addReportLineDelta(expectedReport, FOOD, FOOD_NORM, oneHundredDelta);
        addReportLineDelta(expectedReport, STEPS, STEPS_NORM, oneHundredDelta);

        assertEquals(expectedReport, healthMe.createReportDelta());
    }


    @Test
    public void WeekReportDeltaHundredPercentTest() {
        HashSet<ReportLineDelta> expectedReport = new HashSet<>(ACTIVITIES_NUMBER);

        Map<Integer, Double> zeroDelta = new HashMap<Integer, Double>() {{
            for (int day = 1; day <= DAYS_IN_WEEK; day++) {
                put(day, 0.0);
            }
        }};

        addReportLineDelta(expectedReport, LIQUID, LIQUID_NORM, zeroDelta);
        addReportLineDelta(expectedReport, FOOD, FOOD_NORM, zeroDelta);
        addReportLineDelta(expectedReport, STEPS, STEPS_NORM, zeroDelta);

        for (int day = 1; day <= DAYS_IN_WEEK; day++) {
            healthMe.takeActivity(day, LIQUID, LIQUID_NORM);
            healthMe.takeActivity(day, FOOD, FOOD_NORM);
            healthMe.takeActivity(day, STEPS, STEPS_NORM);
        }

        assertEquals(expectedReport, healthMe.createReportDelta());
    }

    @Test
    public void WeekReportDeltaTenPercentTest() {
        HashSet<ReportLineDelta> expectedReport = new HashSet<>(ACTIVITIES_NUMBER);

        Map<Integer, Double> tenPercentDelta = new HashMap<Integer, Double>() {{
            for (int day = 1; day <= DAYS_IN_WEEK; day++) {
                put(day, 90.0);
            }
        }};

        addReportLineDelta(expectedReport, LIQUID, LIQUID_NORM, tenPercentDelta);
        addReportLineDelta(expectedReport, FOOD, FOOD_NORM, tenPercentDelta);
        addReportLineDelta(expectedReport, STEPS, STEPS_NORM, tenPercentDelta);

        for (int day = 1; day <= DAYS_IN_WEEK; day++) {
            healthMe.takeActivity(day, LIQUID, (int) (LIQUID_NORM * 0.1));
            healthMe.takeActivity(day, FOOD, (int) (FOOD_NORM * 0.1));
            healthMe.takeActivity(day, STEPS, (int) (STEPS_NORM * 0.1));
        }

        assertEquals(expectedReport, healthMe.createReportDelta());
    }


}
