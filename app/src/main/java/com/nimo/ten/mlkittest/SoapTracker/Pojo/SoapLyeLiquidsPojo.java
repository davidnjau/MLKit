package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class SoapLyeLiquidsPojo {

    private String NaOh;
    private String Liquids;

    public SoapLyeLiquidsPojo(String naOh, String liquids) {
        NaOh = naOh;
        Liquids = liquids;
    }

    public SoapLyeLiquidsPojo() {
    }

    public String getNaOh() {
        return NaOh;
    }

    public void setNaOh(String naOh) {
        NaOh = naOh;
    }

    public String getLiquids() {
        return Liquids;
    }

    public void setLiquids(String liquids) {
        Liquids = liquids;
    }
}
