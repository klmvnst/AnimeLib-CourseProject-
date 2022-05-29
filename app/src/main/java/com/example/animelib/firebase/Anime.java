package com.example.animelib.firebase;

public class Anime {

    private String name;
    private String genre;
    private String episodes;
    private String duration;
    private String image;
    private String video;
    private String type;
    private String description;
    private String date;
    private String KEY;
    private Boolean isPopularity;

    public Anime(){
    }

    public Anime(String name, String genre, String episodes, String duration, String image, String video, String type, String description, String date, String key, Boolean isPopularity) {
        this.name = name;
        this.genre = genre;
        this.episodes = episodes;
        this.duration = duration;
        this.image = image;
        this.video = video;
        this.type = type;
        this.description = description;
        this.date = date;
        this.KEY = key;
        this.isPopularity = isPopularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return KEY;
    }

    public void setKey(String KEY) {
        this.KEY = KEY;
    }

    public Boolean getPopularity() {
        return isPopularity;
    }

    public void setPopularity(Boolean popularity) {
        isPopularity = popularity;
    }
}
