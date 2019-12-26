package com.vivek.omdb;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

import java.lang.ref.WeakReference;

public class OmdbApp extends Application {
    private static WeakReference<Context> context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<Context>(this);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    public static Context getContext() {
        return context.get();
    }
}
