package com.example.holmesvinn.the_movie_directory;

/**
 * Created by Holmes Vinn on 29-Jun-16.
 */
public class ModelClass {

    private String poster;

    private String Overview;
    private String title;
    private String language;
    private String backposter;
    private int vote;
    private double average;
    private String date;

    public String getDate() {
        return date;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return Overview;
    }

    public String getBackposter() {
        return backposter;
    }

    public int getVote() {
        return vote;
    }

    public double getAverage() {
        return average;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public void setPoster(String poster) {
        this.poster = poster;
    }



    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    public void setBackposter(String backposter) {
        this.backposter = backposter;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }



    public void setAverage(double average) {
        this.average = average;
    }
}
