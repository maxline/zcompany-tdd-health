package com.zcompany.health;

public class ReportLineDayLeft {
    private EActivity eActivity;
    private int dayLeft;

    public ReportLineDayLeft(EActivity eActivity, int dayLeft) {
        this.eActivity = eActivity;
        this.dayLeft = dayLeft;
    }

    @Override
    public String toString() {
        return "Line{" +
                eActivity +
                ", delta=" + dayLeft +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportLineDayLeft that = (ReportLineDayLeft) o;

        if (dayLeft != that.dayLeft) return false;
        return eActivity == that.eActivity;
    }

    @Override
    public int hashCode() {
        int result = eActivity != null ? eActivity.hashCode() : 0;
        result = 31 * result + dayLeft;
        return result;
    }
}
