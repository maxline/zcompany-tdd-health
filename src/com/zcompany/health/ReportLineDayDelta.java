package com.zcompany.health;

public class ReportLineDayDelta {
    private EActivity eActivity;
    private double percentDelta;

    public ReportLineDayDelta(EActivity eActivity, double percentDelta) {
        this.eActivity = eActivity;
        this.percentDelta = percentDelta;
    }

    @Override
    public String toString() {
        return "Line{" +
                eActivity +
                ", delta=" + percentDelta +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportLineDayDelta that = (ReportLineDayDelta) o;

        if (percentDelta != that.percentDelta) return false;
        return eActivity == that.eActivity;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = eActivity != null ? eActivity.hashCode() : 0;
        temp = Double.doubleToLongBits(percentDelta);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
