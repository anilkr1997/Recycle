package com.v24.recycle.Modelclass;

public class filtermodelclass {
    private String ID;
    private String KEY_DATE;
    private String KEY_TL_FARE;

    public filtermodelclass(String id, String key_date, String key_tl_fare) {
        ID = id;
        KEY_DATE = key_date;
        KEY_TL_FARE = key_tl_fare;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getKEY_DATE() {
        return KEY_DATE;
    }

    public void setKEY_DATE(String KEY_DATE) {
        this.KEY_DATE = KEY_DATE;
    }

    public String getKEY_TL_FARE() {
        return KEY_TL_FARE;
    }

    public void setKEY_TL_FARE(String KEY_TL_FARE) {
        this.KEY_TL_FARE = KEY_TL_FARE;
    }
}
