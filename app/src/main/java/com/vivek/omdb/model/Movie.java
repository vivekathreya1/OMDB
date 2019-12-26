package com.vivek.omdb.model;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "bookmarkTable")
public class Movie extends BaseObservable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "year")
    private String year;
    @ColumnInfo (name = "poster")
    private String posterLink;
    @ColumnInfo (name = "imdbId")
    private String imdbId;

    public Movie(String imdbId, String title, String year, String posterLink) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.posterLink = posterLink;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLinkl) {
        this.posterLink = posterLinkl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
