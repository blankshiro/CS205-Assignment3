package com.example.cs205t8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Deleter {

    private File FileDir;
    private ExecutorService executorService;

    public Saver(String FileDirName){
        // create new directory in storage
        this.FileDir = new File(FileDirName);
        if (!FileDir.exists()) {
            // Break because no such directory
            Log.i("CS205:","no such directory " + FileDir);
            break;
        }
        // Log the information for debugging purposes
        Log.i("CS205:","created directory " + FileDir);
        this.executorService = Executors.newFixedThreadPool(3);
    }

    public void DeleteImage(String url){
        executorService.submit(new DeleteTask(url, FileDir));
    }
}

class DeleteTask implements Runnable{

    private String url;
    private File FileDir;

    public DeleteTask(String url, File FileDir){
        this.url = url;
        this.FileDir = FileDir;
    }

    @Override
    public void run(){
        try {
            String filename = String.valueOf(url.hashCode());
            File file = new File(FileDir, filename);
            file.delete();
            Log.e("CS205:", "deleted " + file);
            os.close();
        } catch (Exception e){
            Log.e("CS205:", e.getMessage());
        }
    }
}
