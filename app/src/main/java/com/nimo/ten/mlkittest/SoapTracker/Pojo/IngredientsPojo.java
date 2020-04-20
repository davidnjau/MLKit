package com.nimo.ten.mlkittest.SoapTracker.Pojo;

import android.content.SharedPreferences;

public class IngredientsPojo {

    private String id;
    private String ingredient_name;
    private String grams;
    private String percentage;

    private String soap_id;

    private String notes;
    private String priority;

    public IngredientsPojo(String id, String ingredient_name, String grams, String percentage, String soap_id, String notes, String priority) {
        this.id = id;
        this.ingredient_name = ingredient_name;
        this.grams = grams;
        this.percentage = percentage;
        this.soap_id = soap_id;
        this.notes = notes;
        this.priority = priority;
    }

    public IngredientsPojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public String getGrams() {
        return grams;
    }

    public void setGrams(String grams) {
        this.grams = grams;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getSoap_id() {
        return soap_id;
    }

    public void setSoap_id(String soap_id) {
        this.soap_id = soap_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
