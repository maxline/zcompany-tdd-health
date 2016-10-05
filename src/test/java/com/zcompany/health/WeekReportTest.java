package com.zcompany.health;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

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

    private void addReportLineDelta(HashSet<ReportLineDelta> report, EActivity eActivity, int valuePlan, Map<Integer, Double> activityDelta) {
        report.add(new ReportLineDelta(eActivity, activityDelta));
    }

    @Test
    public void weekReportDeltaZeroPercentTest() {
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
    public void weekReportDeltaHundredPercentTest() {
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
    public void weekReportDeltaTenPercentTest() {
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

    /**
     * fact 0% 0% 0% _0%_ 0% 0% 0%
     * delta 100% 100% 100% _100%_ 100% 100% 100%
     * sorted 100% 100% 100% _100%_ 100% 100% 100%
     * median = 100%
     */
    @Test
    public void medianZeroPercentTest() {
        List<Double> expectedMedian = Arrays.asList(100.0, 100.0, 100.0);

        assertEquals(expectedMedian, healthMe.createReportMedian());
    }

    /**
     * fact 100% 100% 100% _100%_ 100% 100% 100%
     * delta 0% 0% 0% _0%_ 0% 0% 0%
     * sorted 0% 0% 0% _0%_ 0% 0% 0%
     * median = 0%
     */
    @Test
    public void medianHundredPercentTest() {
        List<Double> expectedMedian = Arrays.asList(0.0, 0.0, 0.0);

        for (int day = 1; day <= DAYS_IN_WEEK; day++) {
            healthMe.takeActivity(day, LIQUID, LIQUID_NORM);
            healthMe.takeActivity(day, FOOD, FOOD_NORM);
            healthMe.takeActivity(day, STEPS, STEPS_NORM);
        }

        assertEquals(expectedMedian, healthMe.createReportMedian());
    }

    /**
     * fact 10% 20% 30% _40%_ 50% 60% 70%
     * delta 90% 80% 70% _60%_ 50% 40% 30%
     * sorted 30% 40% 50% _60%_ 70% 80% 90%
     * median = 60%
     */
    @Test
    public void medianSixtyPercentTest() {
        List<Double> expectedMedian = Arrays.asList(60.0, 60.0, 60.0);

        for (int day = 1; day <= DAYS_IN_WEEK; day++) {
            healthMe.takeActivity(day, LIQUID, (int) (LIQUID_NORM * 0.1 * day));
            healthMe.takeActivity(day, FOOD, (int) (FOOD_NORM * 0.1 * day));
            healthMe.takeActivity(day, STEPS, (int) (STEPS_NORM * 0.1 * day));
        }

        assertEquals(expectedMedian, healthMe.createReportMedian());
    }

    /**
     * fact 70% 10% 10% _10%_ 90% 90% 90%
     * delta 30% 90% 90% _90%_ 10% 10% 10%
     * sorted 10% 10% 10% _30%_ 90% 90% 90%
     * median = 30%
     */
    @Test
    public void medianThirtyPercentTest() {
        List<Double> expectedMedian = Arrays.asList(30.0, 30.0, 30.0);

        for (int day = 1; day <= 1; day++) {
            healthMe.takeActivity(day, LIQUID, (int) (LIQUID_NORM * 0.7));
            healthMe.takeActivity(day, FOOD, (int) (FOOD_NORM * 0.7));
            healthMe.takeActivity(day, STEPS, (int) (STEPS_NORM * 0.7));
        }

        for (int day = 2; day <= 4; day++) {
            healthMe.takeActivity(day, LIQUID, (int) (LIQUID_NORM * 0.9));
            healthMe.takeActivity(day, FOOD, (int) (FOOD_NORM * 0.9));
            healthMe.takeActivity(day, STEPS, (int) (STEPS_NORM * 0.9));
        }

        for (int day = 5; day <= DAYS_IN_WEEK; day++) {
            healthMe.takeActivity(day, LIQUID, (int) (LIQUID_NORM * 0.1));
            healthMe.takeActivity(day, FOOD, (int) (FOOD_NORM * 0.1));
            healthMe.takeActivity(day, STEPS, (int) (STEPS_NORM * 0.1));
        }

        assertEquals(expectedMedian, healthMe.createReportMedian());
    }
}
