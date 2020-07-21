package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class RecipeDetailsPojo {

    private String id;
    private String RecipeName;
    private String Date_in;
    private String LiquidWeight;
    private String LyeWeight;
    private String LyeRatio;
    private String Liquid;
    private String OilWeight;
    private String EssentialOil;
    private String EssentialRatio;
    private String SuperFat;
    private String TotalWeight;
    private String firebaseKey;

    public RecipeDetailsPojo(String id, String recipeName, String date_in, String liquidWeight, String lyeWeight, String lyeRatio, String liquid, String oilWeight, String essentialOil, String essentialRatio, String superFat, String totalWeight, String firebaseKey) {
        this.id = id;
        RecipeName = recipeName;
        Date_in = date_in;
        LiquidWeight = liquidWeight;
        LyeWeight = lyeWeight;
        LyeRatio = lyeRatio;
        Liquid = liquid;
        OilWeight = oilWeight;
        EssentialOil = essentialOil;
        EssentialRatio = essentialRatio;
        SuperFat = superFat;
        TotalWeight = totalWeight;
        this.firebaseKey = firebaseKey;
    }

    public RecipeDetailsPojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public String getDate_in() {
        return Date_in;
    }

    public void setDate_in(String date_in) {
        Date_in = date_in;
    }

    public String getLiquidWeight() {
        return LiquidWeight;
    }

    public void setLiquidWeight(String liquidWeight) {
        LiquidWeight = liquidWeight;
    }

    public String getLyeWeight() {
        return LyeWeight;
    }

    public void setLyeWeight(String lyeWeight) {
        LyeWeight = lyeWeight;
    }

    public String getLyeRatio() {
        return LyeRatio;
    }

    public void setLyeRatio(String lyeRatio) {
        LyeRatio = lyeRatio;
    }

    public String getLiquid() {
        return Liquid;
    }

    public void setLiquid(String liquid) {
        Liquid = liquid;
    }

    public String getOilWeight() {
        return OilWeight;
    }

    public void setOilWeight(String oilWeight) {
        OilWeight = oilWeight;
    }

    public String getEssentialOil() {
        return EssentialOil;
    }

    public void setEssentialOil(String essentialOil) {
        EssentialOil = essentialOil;
    }

    public String getEssentialRatio() {
        return EssentialRatio;
    }

    public void setEssentialRatio(String essentialRatio) {
        EssentialRatio = essentialRatio;
    }

    public String getSuperFat() {
        return SuperFat;
    }

    public void setSuperFat(String superFat) {
        SuperFat = superFat;
    }

    public String getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        TotalWeight = totalWeight;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}

