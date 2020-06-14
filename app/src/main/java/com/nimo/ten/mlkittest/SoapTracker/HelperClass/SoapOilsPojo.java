package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

public class SoapOilsPojo {

    private String oil_name;
    private String naoh_weight;
    private String id;
    private String oilWeight;

    public SoapOilsPojo(String oil_name, String naoh_weight, String id, String oilWeight) {
        this.oil_name = oil_name;
        this.naoh_weight = naoh_weight;
        this.id = id;
        this.oilWeight = oilWeight;
    }

    public SoapOilsPojo() {
    }

    public String getOil_name() {
        return oil_name;
    }

    public void setOil_name(String oil_name) {
        this.oil_name = oil_name;
    }

    public String getNaoh_weight() {
        return naoh_weight;
    }

    public void setNaoh_weight(String naoh_weight) {
        this.naoh_weight = naoh_weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOilWeight() {
        return oilWeight;
    }

    public void setOilWeight(String oilWeight) {
        this.oilWeight = oilWeight;
    }
}
