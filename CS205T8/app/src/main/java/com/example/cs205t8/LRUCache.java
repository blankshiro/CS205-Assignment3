package com.example.cs205t8;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.LinkedHashMap;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private MainActivity activity;
    private long limit;
    private long currSize;

    /**
     * Constructor for the LRUCache.
     *
     * @param activity The current MainActivity.
     * @param limit The limit of the LRUCache.
     */
    public LRUCache(MainActivity activity, long limit) {
        super(100, 0.75f, true);
        this.activity = activity;
        this.limit = limit;
    }

    /**
     * This method gets the limit of the LRUCache.
     * 
     * @return The limit of the LRUCache.
     */
    public long getLimit() { return limit; }

    /**
     * This method gets the current size of the LRUCache.
     * 
     * @return The current size of the LRUCache.
     */
    public long getCurrSize() { return currSize; }


    /**
     * This method is automatically called after the put method.
     * Checks if the addition of the current image exceeds the conditon.
     * If it returns true, the eldest entry will be removed
     *
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        long priorCurrSize = currSize;
        if(priorCurrSize > limit){
            Bitmap b = (Bitmap)eldest.getValue();
            currSize -= b.getByteCount();
            Log.i("CS205 - LRU Cache(" + currSize/1024 +"):","removing eldest image: " + eldest.getKey() + " size: " + b.getByteCount()/1024);
            activity.sendMessageUI(currSize / 1024 + " KB used out of available cache of "
                    + limit / 1024 + " KB");
        }
        return priorCurrSize > limit;
    }

    /**
     * This method puts the specified image into the LRUCache.
     * 
     * @param key The url of the image to be cached.
     * @param value The bitmap to be cached.
     */
    @Override
    public V put(K key, V value){
        Bitmap b = (Bitmap)value;
        currSize += b.getByteCount();

        boolean noFit = currSize > limit; // Check if addition of new image will exceed the cache limit
        Log.i("CS205 - LRU Cache(" + currSize/1024 +"):","noFit check: " + currSize + " limit: " + limit);

        while(noFit && this.size() != 0){   // While the cache limit is still exceeded, keep removing the eldest entry
            if(currSize <= limit){
                noFit = false;
                break;
            }
            Entry eldest = (Entry) this.entrySet().toArray()[0];   // Get the eldest entry
            Bitmap eldest_b = (Bitmap)eldest.getValue();
            Log.i("CS205 - LRU Cache:","removing image: " + eldest.getKey() + " size: " + b.getByteCount()/1024);
            this.remove(eldest.getKey());
            currSize -= eldest_b.getByteCount();
        }
        Log.i("CS205 - LRU Cache(" + currSize/1024 +"):","putting image: " + key + " size: " + b.getByteCount()/1024);
        activity.sendMessageUI(currSize / 1024 + " KB used out of available cache of "
                + limit / 1024 + " KB");
        return super.put(key, value);
    }

    /**
     * This method clears the LRUCache.
     */
    @Override
    public void clear() {
        super.clear();
        this.currSize = 0;
    }
}