package com.example.top10downloaderapp;

public class Feedentry {
    private String name;
    private String artist;
    private String releaseDate;
    private String summary;
    private String Imageurl;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }



    @Override
    public String toString() {
        return
                "name=" + name + '\n' +
                " artist=" + artist + '\n'+
                " releaseDate=" + releaseDate + '\n' +
                "Imageurl=" + Imageurl + '\n'

              ;
    }
}
