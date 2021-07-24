package com.v24.recycle.Modelclass;

public class getUrllist {
    private String id;
    private String Url;
    private String Urlid;



    public getUrllist(String id, String url, String urlid) {
        this.id = id;
        Url = url;
        Urlid = urlid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
    public String getUrlid() {
        return Urlid;
    }

    public void setUrlid(String urlid) {
        Urlid = urlid;
    }
}
