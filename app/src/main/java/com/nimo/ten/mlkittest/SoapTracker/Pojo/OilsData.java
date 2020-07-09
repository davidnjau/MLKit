package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class OilsData {

    private String SoapId;
    private double OilWeight;

    public OilsData(String soapId, double oilWeight) {
        SoapId = soapId;
        OilWeight = oilWeight;
    }

    public OilsData() {
    }

    public String getSoapId() {
        return SoapId;
    }

    public void setSoapId(String soapId) {
        SoapId = soapId;
    }

    public double getOilWeight() {
        return OilWeight;
    }

    public void setOilWeight(double oilWeight) {
        OilWeight = oilWeight;
    }
}
