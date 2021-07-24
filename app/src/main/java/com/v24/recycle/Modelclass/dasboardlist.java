package com.v24.recycle.Modelclass;

public class dasboardlist {
    int id;
    int imageurl;
    String dasbordname;

    public dasboardlist(int id, int imageurl, String dasbordname) {
        this.id = id;
        this.imageurl = imageurl;
        this.dasbordname = dasbordname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageurl() {
        return imageurl;
    }

    public void setImageurl(int imageurl) {
        this.imageurl = imageurl;
    }

    public String getDasbordname() {
        return dasbordname;
    }

    public void setDasbordname(String dasbordname) {
        this.dasbordname = dasbordname;
    }
}
