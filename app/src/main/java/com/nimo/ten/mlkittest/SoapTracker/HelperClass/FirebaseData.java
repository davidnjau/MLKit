package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

public class FirebaseData {

    private String recipe_name;
    private String liquid_weight;
    private String naoh_weight;
    private String essential_weight;
    private String essential_percentage;
    private String super_fat_percentage;
    private String oil_amount;
    private String recipe_key;

    private String oil_name;
    private String oil_weight;

    public FirebaseData(String recipe_name, String liquid_weight, String naoh_weight, String essential_weight, String essential_percentage, String super_fat_percentage, String oil_amount, String recipe_key, String oil_name, String oil_weight) {
        this.recipe_name = recipe_name;
        this.liquid_weight = liquid_weight;
        this.naoh_weight = naoh_weight;
        this.essential_weight = essential_weight;
        this.essential_percentage = essential_percentage;
        this.super_fat_percentage = super_fat_percentage;
        this.oil_amount = oil_amount;
        this.recipe_key = recipe_key;
        this.oil_name = oil_name;
        this.oil_weight = oil_weight;
    }

    public FirebaseData() {
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getLiquid_weight() {
        return liquid_weight;
    }

    public void setLiquid_weight(String liquid_weight) {
        this.liquid_weight = liquid_weight;
    }

    public String getNaoh_weight() {
        return naoh_weight;
    }

    public void setNaoh_weight(String naoh_weight) {
        this.naoh_weight = naoh_weight;
    }

    public String getEssential_weight() {
        return essential_weight;
    }

    public void setEssential_weight(String essential_weight) {
        this.essential_weight = essential_weight;
    }

    public String getEssential_percentage() {
        return essential_percentage;
    }

    public void setEssential_percentage(String essential_percentage) {
        this.essential_percentage = essential_percentage;
    }

    public String getSuper_fat_percentage() {
        return super_fat_percentage;
    }

    public void setSuper_fat_percentage(String super_fat_percentage) {
        this.super_fat_percentage = super_fat_percentage;
    }

    public String getOil_amount() {
        return oil_amount;
    }

    public void setOil_amount(String oil_amount) {
        this.oil_amount = oil_amount;
    }

    public String getRecipe_key() {
        return recipe_key;
    }

    public void setRecipe_key(String recipe_key) {
        this.recipe_key = recipe_key;
    }

    public String getOil_name() {
        return oil_name;
    }

    public void setOil_name(String oil_name) {
        this.oil_name = oil_name;
    }

    public String getOil_weight() {
        return oil_weight;
    }

    public void setOil_weight(String oil_weight) {
        this.oil_weight = oil_weight;
    }
}
