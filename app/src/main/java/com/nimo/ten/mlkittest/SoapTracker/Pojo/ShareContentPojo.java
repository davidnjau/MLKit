package com.nimo.ten.mlkittest.SoapTracker.Pojo;

import java.util.List;

public class ShareContentPojo {

    private List<String> myIngredients;
    private List<String> myNotes;
    private List<String> myLye;

    public ShareContentPojo(List<String> myIngredients, List<String> myNotes, List<String> myLye) {
        this.myIngredients = myIngredients;
        this.myNotes = myNotes;
        this.myLye = myLye;
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

    public List<String> getMyLye() {
        return myLye;
    }

    public void setMyLye(List<String> myLye) {
        this.myLye = myLye;
    }
}
