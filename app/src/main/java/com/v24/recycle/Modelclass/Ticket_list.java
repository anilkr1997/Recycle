package com.v24.recycle.Modelclass;

public class Ticket_list {
    private String ID;
    private String KEY_S_NO;
    private String KEY_DATE;
    private String KEY_TO;
    private String KEY_FROM;
    private String KEY_BARCODE;
    private String KEY_TL_FARE;
    private String KEY_VALIDE;
    private String KEY_VALIDE_UPTO;
    private String KEY_VALIDE_WITHIN;
    private String KEY_FITTER_DDTE;

    public Ticket_list(String ID, String KEY_S_NO, String KEY_DATE, String KEY_TO, String KEY_FROM, String KEY_BARCODE, String KEY_TL_FARE, String KEY_VALIDE, String KEY_VALIDE_UPTO) {
        this.ID = ID;
        this.KEY_S_NO = KEY_S_NO;
        this.KEY_DATE = KEY_DATE;
        this.KEY_TO = KEY_TO;
        this.KEY_FROM = KEY_FROM;
        this.KEY_BARCODE = KEY_BARCODE;
        this.KEY_TL_FARE = KEY_TL_FARE;
        this.KEY_VALIDE = KEY_VALIDE;
        this.KEY_VALIDE_UPTO = KEY_VALIDE_UPTO;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getKEY_S_NO() {
        return KEY_S_NO;
    }

    public void setKEY_S_NO(String KEY_S_NO) {
        this.KEY_S_NO = KEY_S_NO;
    }

    public String getKEY_DATE() {
        return KEY_DATE;
    }

    public void setKEY_DATE(String KEY_DATE) {
        this.KEY_DATE = KEY_DATE;
    }

    public String getKEY_TO() {
        return KEY_TO;
    }

    public void setKEY_TO(String KEY_TO) {
        this.KEY_TO = KEY_TO;
    }

    public String getKEY_FROM() {
        return KEY_FROM;
    }

    public void setKEY_FROM(String KEY_FROM) {
        this.KEY_FROM = KEY_FROM;
    }

    public String getKEY_BARCODE() {
        return KEY_BARCODE;
    }

    public void setKEY_BARCODE(String KEY_BARCODE) {
        this.KEY_BARCODE = KEY_BARCODE;
    }

    public String getKEY_TL_FARE() {
        return KEY_TL_FARE;
    }

    public void setKEY_TL_FARE(String KEY_TL_FARE) {
        this.KEY_TL_FARE = KEY_TL_FARE;
    }

    public String getKEY_VALIDE() {
        return KEY_VALIDE;
    }

    public void setKEY_VALIDE(String KEY_VALIDE) {
        this.KEY_VALIDE = KEY_VALIDE;
    }

    public String getKEY_VALIDE_UPTO() {
        return KEY_VALIDE_UPTO;
    }

    public void setKEY_VALIDE_UPTO(String KEY_VALIDE_UPTO) {
        this.KEY_VALIDE_UPTO = KEY_VALIDE_UPTO;
    }

    public String getKEY_VALIDE_WITHIN() {
        return KEY_VALIDE_WITHIN;
    }

    public void setKEY_VALIDE_WITHIN(String KEY_VALIDE_WITHIN) {
        this.KEY_VALIDE_WITHIN = KEY_VALIDE_WITHIN;
    }
}
