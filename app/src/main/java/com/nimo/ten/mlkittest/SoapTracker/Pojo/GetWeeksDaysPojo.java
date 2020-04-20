package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class GetWeeksDaysPojo {

    private long numberOfWeeks;
    private long numberOfDays;

    private String txtWeeks;
    private String txtDays;

    public GetWeeksDaysPojo(long numberOfWeeks, long numberOfDays, String txtWeeks, String txtDays) {
        this.numberOfWeeks = numberOfWeeks;
        this.numberOfDays = numberOfDays;
        this.txtWeeks = txtWeeks;
        this.txtDays = txtDays;
    }

    public GetWeeksDaysPojo() {
    }

    public long getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(long numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getTxtWeeks() {
        return txtWeeks;
    }

    public void setTxtWeeks(String txtWeeks) {
        this.txtWeeks = txtWeeks;
    }

    public String getTxtDays() {
        return txtDays;
    }

    public void setTxtDays(String txtDays) {
        this.txtDays = txtDays;
    }
}
