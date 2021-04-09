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
        long priorCurrSize = currSize;
        if(priorCurrSize > limit){
            Bitmap b = (Bitmap)eldest.getValue();
            currSize -= b.getByteCount();
            Log.i("CS205 - LRU Cache(" + currSize/1024 +"):","removing eldest image: " + eldest.getKey() + " size: " + b.getByteCount()/1024);
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

        boolean noFit = currSize > limit;
        Log.i("CS205 - LRU Cache(" + currSize/1024 +"):","noFit " + currSize + " limit: " + limit);

        while(noFit){
            Entry eldest = (Entry) this.entrySet().toArray()[this.size() -1];
            Bitmap eldest_b = (Bitmap)eldest.getValue();
            if(currSize - eldest_b.getByteCount() < limit){
                noFit = false;
                break;
            }
            this.remove(eldest.getKey());
            currSize -= eldest_b.getByteCount();
            Log.i("CS205 - LRU Cache(" + currSize/1024 +"):","noFit - removing eldest image: " + eldest.getKey() + " size: " + eldest_b.getByteCount()/1024);
        }
        Log.i("CS205 - LRU Cache(" + currSize/1024 +"):","putting image: " + key + " size: " + b.getByteCount()/1024);
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