package com.v24.recycle.Modelclass;

public class stationfarelist {
    private  String id;
    private  String S_From;
    private  String S_To;
    private  String S_Fare;

    public stationfarelist( String s_From, String s_To, String s_Fare) {
        //this.id = id;
        S_From = s_From;
        S_To = s_To;
        S_Fare = s_Fare;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getS_From() {
        return S_From;
    }

    public void setS_From(String s_From) {
        S_From = s_From;
    }

    public String getS_To() {
        return S_To;
    }

    public void setS_To(String s_To) {
        S_To = s_To;
    }

    public String getS_Fare() {
        return S_Fare;
    }

    public void setS_Fare(String s_Fare) {
        S_Fare = s_Fare;
    }
}
