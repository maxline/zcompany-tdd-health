package com.zcompany.health;

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

    private int liquid;
    private int food;
    private int liquidNorm;
    private int foodNorm;
    private int stepsNorm;

    public HealthMe(int liquidNorm, int foodNorm, int stepsNorm) {
        this.liquidNorm = liquidNorm;
        this.foodNorm = foodNorm;
        this.stepsNorm = stepsNorm;
    }

    public void drinkWater(int liquidParam) {
        liquid += liquidParam;
    }

    public void haveEating(int liquidParam, int foodParam) {
        liquid += liquidParam;
        food += foodParam;

    }

    public int getLiquidLeft() {
        return liquidNorm - liquid;
    }

    public int getFoodLeft() {
        return foodNorm - food;
    }
}
