package com.eugene.spacecenter.data.models;

import com.google.gson.annotations.SerializedName;

public class Apod{

	@SerializedName("date")
	private String date;

	@SerializedName("copyright")
	private String copyright;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("hdurl")
	private String hdurl;

	@SerializedName("service_version")
	private String serviceVersion;

	@SerializedName("explanation")
	private String explanation;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	public Apod(String date, String explanation, String title, String url, String hdurl) {
		this.date = date;
		this.hdurl = hdurl;
		this.explanation = explanation;
		this.title = title;
		this.url = url;
	}

	public void setDate(String date){this.date = date;}
	public String getDate(){return date;}

	public void setCopyright(String copyright){this.copyright = copyright;}
	public String getCopyright(){return copyright;}

	public void setMediaType(String mediaType){this.mediaType = mediaType;}
	public String getMediaType(){return mediaType;}

	public void setHdurl(String hdurl){this.hdurl = hdurl;}
	public String getHdurl(){return hdurl;}

	public void setServiceVersion(String serviceVersion){this.serviceVersion = serviceVersion;}
	public String getServiceVersion(){return serviceVersion;}

	public void setExplanation(String explanation){this.explanation = explanation;}
	public String getExplanation(){return explanation;}

	public void setTitle(String title){this.title = title;}

	public String getTitle(){return title;}

	public void setUrl(String url){this.url = url;}
	public String getUrl(){return url;}
}