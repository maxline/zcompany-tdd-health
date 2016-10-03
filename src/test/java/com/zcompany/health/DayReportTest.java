package com.zcompany.health;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static com.zcompany.health.DayNorm.*;
import static com.zcompany.health.EActivity.*;
import static com.zcompany.health.HealthMe.ACTIVITIES_NUMBER;
import static org.junit.Assert.assertEquals;

public class DayReportTest {

    private static final int TODAY = 1;
    private HealthMe healthMe;

    @Before
    public void setup() {
        healthMe = new HealthMe(LIQUID_NORM, FOOD_NORM, STEPS_NORM);
    }

//    private double countPercentDelta(int valuePlan, int valueFact) {
//        return 100 * (valuePlan - valueFact) / valuePlan;
//    }

    private void addReportLineDayLeft(HashSet<ReportLineDayLeft> report, EActivity eActivity, int valuePlan, int valueFact) {
        report.add(new ReportLineDayLeft(eActivity, valuePlan - valueFact));
    }

    @Test
    public void dayReportLeftZeroPercentOfNormTest() {
        HashSet<ReportLineDayLeft> report = new HashSet<>(ACTIVITIES_NUMBER);

        addReportLineDayLeft(report, LIQUID, LIQUID_NORM, 0);
        addReportLineDayLeft(report, FOOD, FOOD_NORM, 0);
        addReportLineDayLeft(report, STEPS, STEPS_NORM, 0);

        assertEquals(report, healthMe.createReportDayLeft(TODAY));
    }

    @Test
    public void dayReportLeftHundredPercentTest() {
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
    public void dayReportLeftTenPercentTest() {
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
    public void dayReportLeftThreeHundredPercentTest() {
        healthMe.takeActivity(TODAY, LIQUID, LIQUID_NORM * 3);
        healthMe.takeActivity(TODAY, FOOD, FOOD_NORM * 3);
        healthMe.takeActivity(TODAY, STEPS, STEPS_NORM * 3);

        HashSet<ReportLineDayLeft> report = new HashSet<>(ACTIVITIES_NUMBER);

        addReportLineDayLeft(report, LIQUID, LIQUID_NORM, LIQUID_NORM * 3);
        addReportLineDayLeft(report, FOOD, FOOD_NORM, FOOD_NORM * 3);
        addReportLineDayLeft(report, STEPS, STEPS_NORM, STEPS_NORM * 3);

        assertEquals(report, healthMe.createReportDayLeft(TODAY));
    }

}
