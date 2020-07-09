package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class EssentialsOilsData {

    private double liquid_weight;
    private double lye_weight;
    private double total_weight;

    public EssentialsOilsData(double liquid_weight, double lye_weight, double total_weight) {
        this.liquid_weight = liquid_weight;
        this.lye_weight = lye_weight;
        this.total_weight = total_weight;
    }

    public EssentialsOilsData() {
    }

    public double getLiquid_weight() {
        return liquid_weight;
    }

    public void setLiquid_weight(double liquid_weight) {
        this.liquid_weight = liquid_weight;
    }

    public double getLye_weight() {
        return lye_weight;
    }

    public void setLye_weight(double lye_weight) {
        this.lye_weight = lye_weight;
    }

    public double getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(double total_weight) {
        this.total_weight = total_weight;
    }
}
