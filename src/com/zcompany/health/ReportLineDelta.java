package com.zcompany.health;

import java.util.HashMap;
import java.util.Map;

public class ReportLineDelta {
    private static final int DAYS_NUMBER = 7;

    private EActivity eActivity;
    //private double percentDelta;

    private Map<Integer, Double> activityDelta = new HashMap<Integer, Double>() {{
        for (int day = 1; day <= DAYS_NUMBER; day++) {
            put(day, 0.0);
        }
    }};

    private double countPercentDelta(int valuePlan, int valueFact) {
        return 100 * (valuePlan - valueFact) / valuePlan;
    }


    public ReportLineDelta(EActivity eActivity, Map<Integer, Double> activityDelta) {
        this.eActivity = eActivity;
        this.activityDelta = activityDelta;
    }

    public ReportLineDelta(EActivity eActivity, int activityPlan, Map<Integer, Integer> activityFact) {
        this.eActivity = eActivity;
        for (int day = 1; day <= DAYS_NUMBER; day++) {
            activityDelta.put(day, countPercentDelta(activityPlan, activityFact.get(day)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportLineDelta that = (ReportLineDelta) o;

        if (eActivity != that.eActivity) return false;
        return activityDelta != null ? activityDelta.equals(that.activityDelta) : that.activityDelta == null;

    }

    @Override
    public int hashCode() {
        int result = eActivity != null ? eActivity.hashCode() : 0;
        result = 31 * result + (activityDelta != null ? activityDelta.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Line{" +
                 eActivity +
                ", delta=" + activityDelta +
                '}';
    }


    //    public ReportLineDelta(EActivity eActivity, double percentDelta) {
//        this.eActivity = eActivity;
//        this.percentDelta = percentDelta;
//    }


}
