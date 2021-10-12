package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notice implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("image")
    private String image;

    @SerializedName("file")
    private String file;

    @SerializedName("title")
    private String title;

    @SerializedName("start_date")
    private String start_date;

    @SerializedName("end_date")
    private String end_date;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("description")
    private String description;

    @SerializedName("FullUrl")
    private String FullUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullUrl() {
        return FullUrl;
    }

    public void setFullUrl(String fullUrl) {
        FullUrl = fullUrl;
    }
}
