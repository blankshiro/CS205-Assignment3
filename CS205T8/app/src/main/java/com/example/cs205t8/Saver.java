package com.example.cs205t8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Saver {

    private File FileDir;
    private ExecutorService executorService;

    public Saver(String FileDirName){
        // create new directory in storage
        this.FileDir = new File(FileDirName);
        if (!FileDir.exists()) {
            // Create new directory
            FileDir.mkdirs();
        }
        // Log the information for debugging purposes
        Log.i("CS205:","created directory " + FileDir);
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public void SaveImage(String url, InputStream inputStream){
        executorService.submit(new SaveTask(url, FileDir, inputStream));
    }
}

class SaveTask implements Runnable{

    private String url;
    private File FileDir;
    private InputStream inputStream;

    public SaveTask(String url, File FileDir, InputStream inputStream){
        this.url = url;
        this.FileDir = FileDir;
        this.inputStream = inputStream;
    }

    @Override
    public void run(){
        // use the hash of url string as filename
        // download image from web and copy to storage device
        try {
            // Our filename is simply just a hash of the url string
            Log.i("CS205 - Saver:", "saving - " + url);
            String filename = String.valueOf(url.hashCode());
            File file = new File(FileDir, filename);
            // We create two file streams and copy from source stream to destination stream
            InputStream is = inputStream;
            OutputStream os = new FileOutputStream(file);
            copyStream(is, os);
            os.close();
            Log.i("CS205 - Saver:", "saved - " + filename);
        } catch (Exception e){
            Log.e("CS205:", e.getMessage());
        }
    }

    public void copyStream(InputStream is, OutputStream os) {
        final int buffer_size=1024;
        try {
            byte[] bytes = new byte[buffer_size];
            while (true) {
                int count = is.read(bytes, 0, buffer_size);
                if(count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception e){
            Log.e("CS205:", e.getMessage());
        }
    }
}
