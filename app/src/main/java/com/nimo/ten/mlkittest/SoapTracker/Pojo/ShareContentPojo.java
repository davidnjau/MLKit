package com.nimo.ten.mlkittest.SoapTracker.Pojo;

import java.util.List;

public class ShareContentPojo {

    private List<String> myIngredients;
    private List<String> myNotes;

    public ShareContentPojo(List<String> myIngredients, List<String> myNotes) {
        this.myIngredients = myIngredients;
        this.myNotes = myNotes;
    }

    public ShareContentPojo() {
    }

    public List<String> getMyIngredients() {
        return myIngredients;
    }

    public void setMyIngredients(List<String> myIngredients) {
        this.myIngredients = myIngredients;
    }

    public List<String> getMyNotes() {
        return myNotes;
    }

    public void setMyNotes(List<String> myNotes) {
        this.myNotes = myNotes;
    }
}
