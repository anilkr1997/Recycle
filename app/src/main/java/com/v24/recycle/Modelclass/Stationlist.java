package com.v24.recycle.Modelclass;

public class Stationlist {
    private   String id;
   private String name;

   private String fare;

    public Stationlist(String id,String name , String fare) {
        this.id = id;
        this.name = name;
        this.fare = fare;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
}
