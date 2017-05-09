package app.com.newsfeed.Data.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("picture")
    @Expose
    private Picture picture;
    @SerializedName("feed")
    @Expose
    private Feed feed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}