package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class IngredientsNotesPojo {

    private String txtIngredient;
    private String txtWeight;
    private String txtNotes;

    public IngredientsNotesPojo(String txtIngredient, String txtWeight, String txtNotes) {
        this.txtIngredient = txtIngredient;
        this.txtWeight = txtWeight;
        this.txtNotes = txtNotes;
    }

    public IngredientsNotesPojo() {
    }

    public String getTxtIngredient() {
        return txtIngredient;
    }

    public void setTxtIngredient(String txtIngredient) {
        this.txtIngredient = txtIngredient;
    }

    public String getTxtWeight() {
        return txtWeight;
    }

    public void setTxtWeight(String txtWeight) {
        this.txtWeight = txtWeight;
    }

    public String getTxtNotes() {
        return txtNotes;
    }

    public void setTxtNotes(String txtNotes) {
        this.txtNotes = txtNotes;
    }
}
