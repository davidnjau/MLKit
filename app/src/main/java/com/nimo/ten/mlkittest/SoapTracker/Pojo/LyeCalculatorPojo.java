package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class LyeCalculatorPojo {

    private String txtDbNaohWeight;
    private Double txtNaOH;
    private Double Liquids;

    public LyeCalculatorPojo(String txtDbNaohWeight, Double txtNaOH, Double liquids) {
        this.txtDbNaohWeight = txtDbNaohWeight;
        this.txtNaOH = txtNaOH;
        Liquids = liquids;
    }

    public LyeCalculatorPojo() {
    }

    public String getTxtDbNaohWeight() {
        return txtDbNaohWeight;
    }

    public void setTxtDbNaohWeight(String txtDbNaohWeight) {
        this.txtDbNaohWeight = txtDbNaohWeight;
    }

    public Double getTxtNaOH() {
        return txtNaOH;
    }

    public void setTxtNaOH(Double txtNaOH) {
        this.txtNaOH = txtNaOH;
    }

    public Double getLiquids() {
        return Liquids;
    }

    public void setLiquids(Double liquids) {
        Liquids = liquids;
    }
}
