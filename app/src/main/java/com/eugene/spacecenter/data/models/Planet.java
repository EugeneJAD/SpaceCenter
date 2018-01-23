package com.eugene.spacecenter.data.models;

import com.google.gson.annotations.SerializedName;

public class Planet{

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("url")
	private String url;

	@SerializedName("object")
	private String object;

	private int imageResId;

	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}

	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return url;
	}

	public void setObject(String object){
		this.object = object;
	}
	public String getObject(){
		return object;
	}

	public int getImageResId() {return imageResId;}
	public void setImageResId(int imageResId) {this.imageResId = imageResId;}
}