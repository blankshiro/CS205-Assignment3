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

    public Loader(Cacher cacher) {
        this.executorService = Executors.newFixedThreadPool(3);
        this.cacher = cacher;
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
        executorService.submit(new DownloadTask(url, imageView, cacher));
    }

}

class DownloadTask implements Runnable{

    private String url;
    private ImageView imageView;
    private Cacher cacher;

    public DownloadTask(String url, ImageView imageView, Cacher cacher){
        this.url = url;
        this.imageView = imageView;
        this.cacher = cacher;
    }

    @Override
    public void run(){
        // get the bitmap of image from cache if possible
        // if not, download from web
        try {
            URL imageURL = new URL(url);
            // Try to get from cache
            Bitmap bitmap = cacher.get(url);
            if (bitmap == null) {
                // Download from url as bitmap
                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            }
            // Set the ImageView object with the bitmap downloaded
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {}
    }
}
