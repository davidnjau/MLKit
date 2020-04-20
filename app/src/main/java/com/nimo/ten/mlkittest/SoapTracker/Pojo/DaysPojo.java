package com.nimo.ten.mlkittest.SoapTracker.Pojo;

public class DaysPojo {

    private String id;
    private String DateIn;
    private String DateOut;

    private String SoapName;

    public DaysPojo(String id, String dateIn, String dateOut, String soapName) {
        this.id = id;
        DateIn = dateIn;
        DateOut = dateOut;
        SoapName = soapName;
    }

    public DaysPojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSoapName() {
        return SoapName;
    }

    public void setSoapName(String soapName) {
        SoapName = soapName;
    }
}
