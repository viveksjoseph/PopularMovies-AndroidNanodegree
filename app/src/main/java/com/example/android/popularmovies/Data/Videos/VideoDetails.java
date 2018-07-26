package com.example.android.popularmovies.Data.Videos;

import com.google.gson.annotations.SerializedName;

public class VideoDetails extends java.lang.Object {

    private static final String YOUTUBE_VIDEO_BASE_URL = "https://www.youtube.com/watch?v=";

    private static final String VIDEO_ID = "id";
    private static final String LANGUAGE = "iso_639_1";
    private static final String COUNTRY = "iso_3166_1";
    private static final String VIDEO_KEY = "key";
    private static final String VIDEO_TITLE = "name";
    private static final String VIDEO_SITE = "site";
    private static final String VIDEO_RESOLUTION = "size";
    private static final String VIDEO_TYPE = "type";

    @SerializedName(VIDEO_ID)
    String videoId;
    @SerializedName(LANGUAGE)
    String language;
    @SerializedName(COUNTRY)
    String country;
    @SerializedName(VIDEO_KEY)
    String videoKey;
    @SerializedName(VIDEO_TITLE)
    String videoName;
    @SerializedName(VIDEO_SITE)
    String videoSite;
    @SerializedName(VIDEO_RESOLUTION)
    int resolution;
    @SerializedName(VIDEO_TYPE)
    String videoType;

    public VideoDetails() {
        videoId = "";
        language = "";
        country = "";
        videoKey = "";
        videoName = "No Trailers Found";
        videoSite = "";
        resolution = 0;
        videoType = "";
    }

    public String getVideoId() {
        return videoId;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoSite() {
        return videoSite;
    }

    public int getResolution() {
        return resolution;
    }

    public String getVideoType() {
        return videoType;
    }

    public String getYoutubeUrl() {
        return YOUTUBE_VIDEO_BASE_URL + videoKey;
    }
}
