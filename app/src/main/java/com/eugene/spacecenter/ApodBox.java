package com.eugene.spacecenter;

/**
 * Created by Администратор on 25.10.2016.
 */

public class ApodBox {

    private String date;
    private String explanation;
    private String title;
    private String url;
    private String hdUrl;


    public ApodBox(String date, String explanation, String title, String url, String hdUrl){
        this.date=date;
        this.explanation=explanation;
        this.title=title;
        this.url=url;
        this.hdUrl=hdUrl;
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getHDurl() {return hdUrl;}
}

