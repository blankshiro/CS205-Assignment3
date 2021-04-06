package com.example.cs205t8;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Cacher {

    private LRUCache<String, Bitmap> cache;


    public Cacher(long limit){
        cache = new LRUCache<String, Bitmap>(limit);
        Log.i("CS205: ","cache limit " + cache.getLimit()/1024 + " KB");

    }

    public long getCurrSize(){
        return cache.getCurrSize();
    }

    public long getLimit(){
        return cache.getLimit();
    }


    public Bitmap get(String url){
        // return the bitmap for the image if found, if not return null
        if (cache.containsKey(url)) {
            Log.i("CS205 - Cache:","loaded image - " + url);
            return cache.get(url);
        }
        else {
            return null;
        }
    }

    public void cacheImage(String url, Bitmap bitmap){
        // download image from web, cache it if possible
        try {
//            URL imageURL = new URL(url);
//            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            cache.put(url, bitmap);
        } catch (Exception e) {
            Log.e("CS205:",e.getMessage());
        }
    }

    public void clear(){
        cache.clear();
        Log.i("CS205:","cache cleared - current size:" + cache.getCurrSize());
    }

}

