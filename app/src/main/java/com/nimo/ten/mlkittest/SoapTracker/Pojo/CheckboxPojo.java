package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class CheckboxPojo {

    private String NaOh;
    private String Liquids;
    private boolean isSelected;

    public CheckboxPojo(String naOh, String liquids, boolean isSelected) {
        NaOh = naOh;
        Liquids = liquids;
        this.isSelected = isSelected;
    }

    public CheckboxPojo() {
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

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
