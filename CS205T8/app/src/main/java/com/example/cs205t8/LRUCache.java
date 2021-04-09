package com.example.cs205t8;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.LinkedHashMap;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private long limit;
    private long currSize;

    /**
     * Constructor for the LRUCache.
     * 
     * @param limit The limit of the LRUCache.
     */
    public LRUCache(long limit) {
        super(100, 0.75f, false);
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

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        if(currSize > limit){
            Log.i("CS205 - LRU Cache:","removing eldest image: " + eldest.getKey());
        }
        return currSize > limit;
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
        Log.i("CS205 - LRU Cache:","putting image: " + key);
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