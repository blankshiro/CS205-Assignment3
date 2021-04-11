package com.example.cs205t8;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.Map.*;

public class Cacher {

    private LRUCache<String, Bitmap> cache;
    private MainActivity activity;

    /**
     * Constructor for Cacher.
     * 
     * @param limit The cache limit.
     */
    public Cacher(MainActivity activity, long limit) {
        this.cache = new LRUCache<String, Bitmap>(activity, limit);
        Log.i("CS205: ","cache limit " + cache.getLimit()/1024 + " KB");

    }

    /**
     * This method gets the current size of the cache.
     * 
     * @return The current size of the cache.
     */
    public long getCurrSize(){
        return cache.getCurrSize();
    }

    /**
     * This method gets the cache limit.
     * 
     * @return The cache limit.
     */
    public long getLimit(){
        return cache.getLimit();
    }


    /**
     * This methods gets the bitmap based on the url.
     * 
     * @param url The url of the bitmap.
     * @return The bitmap based on the url.
     */
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

    /**
     * This method puts the image into the cache.
     * 
     * @param url The url of the image to be cached.
     * @param bitmap The bitmap to be cached.
     */
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

    /**
     * This method clears the current cache.
     */
    public void clear(){
        cache.clear();
        Log.i("CS205:","cache cleared - current size:" + cache.getCurrSize());
    }

}

