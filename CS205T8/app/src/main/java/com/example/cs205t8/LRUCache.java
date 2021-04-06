package com.example.cs205t8;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.LinkedHashMap;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private long limit;
    private long currSize;

    public LRUCache(long limit) {
        super(100, 0.75f, false);
        this.limit = limit;
    }

    public long getLimit() { return limit; }

    public long getCurrSize() { return currSize; }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return currSize > limit;
    }

    @Override
    public V put(K key, V value){
        Bitmap b = (Bitmap)value;
        currSize += b.getByteCount();
        Log.i("CS205 - LRU Cache:","putting image: " + key);
        return super.put(key, value);
    }

    @Override
    public void clear() {
        super.clear();
        this.currSize = 0;
    }
}