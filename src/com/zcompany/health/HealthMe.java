package com.zcompany.health;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.zcompany.health.EActivity.*;

/**
 * В течении дня человеку надо выпивать от 2-3.5 литров воды
 * В течении дня человеку надо съедать от 1300-2600ККал
 * В течении дня человеку необходимо двигаться +- 2 часа и преодолевать более 2000 шагов
 * <p>
 * Делаем сервис который все это покажет, стоит ли нам в ближайшее время поесть, попить или прогуляться.
 * <p>
 * 8 утра  - выпить стакан воды
 * 8:30 - позавтракать
 * 10:00 - стакан воды
 * 11:30 - ланч
 * <p>
 * Reporting
 * 1. За сегодня /неделя
 * % насколько выполнили ожидания (% деградации), медиана
 * 2. Сколько мне надо сегодня
 */
public class HealthMe {


    private static final int INITIAL_CAPACITY = 7;

    private int liquidPlan;
    private int foodPlan;
    private int stepsPlan;


    private Map<Integer, Integer> liquidFact = new HashMap<Integer, Integer>() {{
        for (int i = 1; i < INITIAL_CAPACITY; i++) {
            put(i, 0);
        }
    }};

    private Map<Integer, Integer> foodFact = new HashMap<Integer, Integer>() {{
        for (int i = 1; i < INITIAL_CAPACITY; i++) {
            put(i, 0);
        }
    }};

    private Map<Integer, Integer> stepsFact = new HashMap<Integer, Integer>() {{
        for (int i = 1; i < INITIAL_CAPACITY; i++) {
            put(i, 0);
        }
    }};

    public HealthMe(int liquidPlan, int foodPlan, int stepsPlan) {
        this.liquidPlan = liquidPlan;
        this.foodPlan = foodPlan;
        this.stepsPlan = stepsPlan;
    }

    public void takeActivity(int day, EActivity eActivity, int activityQuantity) {
        switch (eActivity) {
            case LIQUID:
                liquidFact.put(day, liquidFact.get(day) + activityQuantity);
                break;
            case FOOD:
                foodFact.put(day, foodFact.get(day) + activityQuantity);
                break;
            case STEPS:
                stepsFact.put(day, stepsFact.get(day) + activityQuantity);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void takeEating(int day, int liquidQuantity, int foodQuantity) {
        takeActivity(day, LIQUID, liquidQuantity);
        takeActivity(day, FOOD, foodQuantity);
    }

    public int getActivityLeft(int day, EActivity eActivity) {
        switch (eActivity) {
            case LIQUID:
                return liquidPlan - liquidFact.get(day);
            case FOOD:
                return foodPlan - foodFact.get(day);
            case STEPS:
                return stepsPlan - stepsFact.get(day);
            default:
                throw new IllegalArgumentException();
        }
    }


    private int countDayLeft(int valuePlan, int valueFact) {
        return valuePlan - valueFact;
    }

//    private double countPercentDelta(int valuePlan, int valueFact) {
//        return 100 * countDayLeft(valuePlan, valueFact) / valuePlan;
//    }

    public Set<ReportLineDayLeft> createReportDayLeft(int day) {
        HashSet<ReportLineDayLeft> report = new HashSet<>(3);
        report.add(new ReportLineDayLeft(LIQUID, countDayLeft(liquidPlan, liquidFact.get(day))));
        report.add(new ReportLineDayLeft(FOOD, countDayLeft(foodPlan, foodFact.get(day))));
        report.add(new ReportLineDayLeft(STEPS, countDayLeft(stepsPlan, stepsFact.get(day))));
        return report;
    }


//    public Set<ReportLineDayDelta> createReportDayDelta() {
//        HashSet<ReportLineDayDelta> report = new HashSet<>(3);
//        report.add(new ReportLineDayDelta(LIQUID, countPercentDelta(liquidPlan, liquidFact)));
//        report.add(new ReportLineDayDelta(FOOD, countPercentDelta(foodPlan, foodFact)));
//        report.add(new ReportLineDayDelta(STEPS, countPercentDelta(stepsPlan, stepsFact)));
//        return report;
//    }
//


}
