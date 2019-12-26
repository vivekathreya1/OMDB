package com.vivek.omdb.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.List;

public class MainModel extends BaseObservable {

    public static final int TYPE_HEADING = 0;
    public static final int TYPE_BOOKMARK = 1;
    public static final int TYPE_SEARCH = 2;

    public static class Builder{
        private int viewType;
        private String heading;
        private List<Movie> bookMarks;
        private List<Movie> searchResults;

        public Builder(int viewType) {
            this.viewType = viewType;
        }

        public Builder setHeading(String heading){
            this.heading = heading;
            return this;
        }

        public Builder setBookMarks(List<Movie> bookMarks){
            this.bookMarks = bookMarks;
            return this;
        }

        public Builder setSearchResult(List<Movie> searchResults){
            this.searchResults = searchResults;
            return this;
        }

        public MainModel build(){
            MainModel mainModel = new MainModel();
            mainModel.bookMarks = this.bookMarks;
            mainModel.heading = this.heading;
            mainModel.searchResults = this.searchResults;
            mainModel.viewType = this.viewType;
            return mainModel;

        }




    }
    private MainModel(){}

    private int viewType;
    private String heading;
    private List<Movie> bookMarks;
    private List<Movie> searchResults;

    @Bindable
    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
        notifyPropertyChanged(BR.viewType);
    }

    @Bindable
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
        notifyPropertyChanged(BR.heading);
    }

    @Bindable
    public List<Movie> getBookMarks() {
        return bookMarks;
    }

    public void setBookMarks(List<Movie> bookMarks) {
        this.bookMarks = bookMarks;
        notifyPropertyChanged(BR.bookMarks);
    }

    @Bindable
    public List<Movie> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Movie> searchResults) {
        this.searchResults = searchResults;
        notifyPropertyChanged(BR.searchResults);
    }
}
