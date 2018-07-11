package com.example.user.mytem;

import android.app.Application;
import android.graphics.Typeface;
import android.support.v4.graphics.TypefaceCompatUtil;

public class FontChange extends Application{
    private Typeface mTypeface;

    @Override
    public void onCreate() {
        super.onCreate();
        mTypeface = Typeface.createFromAsset(getAssets(),"");
    }
}
