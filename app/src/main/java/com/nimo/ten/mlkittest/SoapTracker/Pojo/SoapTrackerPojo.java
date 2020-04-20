package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class SoapTrackerPojo {

    private String id;
    private String Name;
    private String PicPath;
    private String DateIn;
    private String DateOut;
    private byte[] photo;
    private String Duration;
    private String Condition;

    public SoapTrackerPojo(String id, String name, String picPath, String dateIn, String dateOut, byte[] photo, String duration, String condition) {
        this.id = id;
        Name = name;
        PicPath = picPath;
        DateIn = dateIn;
        DateOut = dateOut;
        this.photo = photo;
        Duration = duration;
        Condition = condition;
    }

    public SoapTrackerPojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPicPath() {
        return PicPath;
    }

    public void setPicPath(String picPath) {
        PicPath = picPath;
    }

    public String getDateIn() {
        return DateIn;
    }

    public void setDateIn(String dateIn) {
        DateIn = dateIn;
    }

    public String getDateOut() {
        return DateOut;
    }

    public void setDateOut(String dateOut) {
        DateOut = dateOut;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }
}

