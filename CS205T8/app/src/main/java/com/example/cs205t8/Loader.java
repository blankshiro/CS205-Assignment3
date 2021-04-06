package com.example.cs205t8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loader {

    private ExecutorService executorService;
    private Cacher cacher;
    private DiskLoader diskLoader;
    private Saver saver;

    public Loader(Cacher cacher, DiskLoader diskLoader, Saver saver) {
        this.executorService = Executors.newFixedThreadPool(3);
        this.cacher = cacher;
        this.diskLoader = diskLoader;
        this.saver = saver;
    }

    public void LoadText(String url, TextView textView, int position){
        // extract the 'caption' for image based on the file name excluding 'png'
        // set the value of the text View based to the 'caption'
        int n = url.lastIndexOf(".png");
        int m = url.lastIndexOf('/');
        textView.setText("item " + position + " : " + url.substring(m+1,n));
    }

    public void LoadImage(String url, ImageView imageView){
        // submit a runnable whose task is to set the image View with the correct bitmap
        executorService.submit(new DownloadTask(url, imageView, cacher, diskLoader, saver));
    }

}

class DownloadTask implements Runnable{

    private String url;
    private ImageView imageView;
    private Cacher cacher;
    private DiskLoader diskLoader;
    private Saver saver;

    public DownloadTask(String url, ImageView imageView, Cacher cacher, DiskLoader diskLoader, Saver saver){
        this.url = url;
        this.imageView = imageView;
        this.cacher = cacher;
        this.diskLoader = diskLoader;
        this.saver = saver;
    }

    @Override
    public void run(){
        // get the bitmap of image from cache if possible
        // if not, download from web
        try {
            URL imageURL = new URL(url);
            // Try to get from cache
            Bitmap bitmap = cacher.get(url);
            if (bitmap == null) { // If cache is empty -> try disk
                bitmap = BitmapFactory.decodeStream(diskLoader.getFile(url));
                if(bitmap != null){ // If in disk -> save to cache
                    cacher.cacheImage(url, bitmap);
                }
            }
            if (bitmap == null) { // If disk is also empty -> download
                // Download from url as bitmap
                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                saver.SaveImage(url);
                cacher.cacheImage(url, bitmap);
                bitmap = BitmapFactory.decodeStream(diskLoader.getFile(url));
            }
            // Set the ImageView object with the bitmap downloaded
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {}
    }
}
