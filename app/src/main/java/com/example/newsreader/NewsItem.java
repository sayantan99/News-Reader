package com.example.newsreader;

public class NewsItem {

    private String title;
    private String description;
    private String Link;
    private  String date;

    public NewsItem(String title, String description, String link, String date) {
        this.title = title;
        this.description = description;
        Link = link;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", Link='" + Link + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}