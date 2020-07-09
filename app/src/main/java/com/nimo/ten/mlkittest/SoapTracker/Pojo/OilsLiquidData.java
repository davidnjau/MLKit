package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class OilsLiquidData {

    private double liquid_weight;
    private double lye_weight;

    public OilsLiquidData(double liquid_weight, double lye_weight) {
        this.liquid_weight = liquid_weight;
        this.lye_weight = lye_weight;
    }

    public OilsLiquidData() {
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
}
