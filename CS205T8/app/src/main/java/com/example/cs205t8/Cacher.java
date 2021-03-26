package com.example.cs205t8;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cacher {

    private HashMap<String, Bitmap> cache;
    private long limit = 0;
    private long currSize = 0;

    public Cacher(){
        cache = new HashMap<String, Bitmap>();
        limit = Runtime.getRuntime().maxMemory() * 1/20;
        Log.i("CS205:","max runtime memory " + Runtime.getRuntime().maxMemory()/1024 + " KB");
        Log.i("CS205: ","cache limit " + limit/1024 + " KB");
    }

    public long getCurrSize() {
        return currSize;
    }

    public long getLimit() {
        return limit;
    }

    public Bitmap get(String url){
        // return the bitmap for the image if found, if not return null
        if (cache.containsKey(url)) {
            return cache.get(url);
        }
        else {
            return null;
        }
    }

    public void put(String url, Bitmap bitmap){
        // insert the (url, bitmap) pair to HashMap, if capacity allows
        // note here we still need to perform network operation to get the image size
        Log.i("CS205: ", bitmap.getByteCount()/1024 + "KB");
        if (!cache.containsKey(url)){
            if (currSize + bitmap.getByteCount() < limit){
                cache.put(url, bitmap);
                currSize += bitmap.getByteCount();
                Log.i("CS205:","caching img:" + url);
                Log.i("CS205:","curr cache:" + currSize/1024 + "," + "max cache:" + limit/1024);
            }
        }
    }

    public void cacheImage(String url){
        // download image from web, cache it if possible
        try {
            URL imageURL = new URL(url);
            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            put(url, bitmap);
        } catch (Exception e) {
            Log.e("CS205:",e.getMessage());
        }
    }

    public void clear(){
        cache.clear();
        Log.i("CS205:","cache cleared");
        currSize = 0;
        limit = Runtime.getRuntime().maxMemory() * 1/20;
    }

}

