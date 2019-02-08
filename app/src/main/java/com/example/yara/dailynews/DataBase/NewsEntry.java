package com.example.yara.dailynews.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Yara on 05-Feb-19.
 */
@Entity(tableName = "News")
public class NewsEntry {
    @PrimaryKey(autoGenerate = true)
     private int id ;
     private String title ;
     private String imageUrl;
     private String details;
     private String time;
     private String Resource;
     private String NewsUrl;

    public NewsEntry() {
    }

    @Ignore
    public NewsEntry(String title, String imageUrl, String details, String time, String resource, String newsUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.details = details;
        this.time = time;
        Resource = resource;
        NewsUrl = newsUrl;
    }

    public NewsEntry(int id, String title, String imageUrl, String details, String time, String resource, String newsUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.details = details;
        this.time = time;
        Resource = resource;
        NewsUrl = newsUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResource() {
        return Resource;
    }

    public void setResource(String resource) {
        Resource = resource;
    }

    public String getNewsUrl() {
        return NewsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        NewsUrl = newsUrl;
    }
}
