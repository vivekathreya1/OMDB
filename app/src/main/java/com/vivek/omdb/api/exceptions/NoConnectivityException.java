package com.vivek.omdb.api.exceptions;

import com.vivek.omdb.OmdbApp;
import com.vivek.omdb.R;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return OmdbApp.getContext().getString(R.string.no_internet);
    }
}
