package com.example.cs205t8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiskLoader {

    private File FileDir;
//    private ExecutorService executorService;

    public DiskLoader(String FileDirName){
        Log.i("CS205:","creating diskLoader");
        // create new directory in storage
        this.FileDir = new File(FileDirName);
//        if (!FileDir.exists()) { // Should not enter here
//            // Create new directory
//            FileDir.mkdirs();
//        }
        // Log the information for debugging purposes
        Log.i("CS205:","disk load created directory " + FileDir);
//        this.executorService = Executors.newFixedThreadPool(3);
    }

    public InputStream getFile(String url){
        try {
            // Our filename is simply just a hash of the url string
            String filename = String.valueOf(url.hashCode());
            File file = new File(FileDir, filename);
//            URL imageURL = new URL(url);
//            // We create two file streams and copy from source stream to destination stream
//            InputStream is = imageURL.openConnection().getInputStream();
            InputStream is = new FileInputStream(file);
            Log.i("CS205 - Disk Loader:", "found a file - " + url);
            return is;
        } catch (Exception e){
            Log.e("CS205 - Disk Loader:", e.getMessage());
            return null;
        }
    }




//    public void SaveImage(String url){
//        executorService.submit(new SaveTask(url, FileDir));
//    }
}

//class SaveTask implements Runnable{
//
//    private String url;
//    private File FileDir;
//
//    public SaveTask(String url, File FileDir){
//        this.url = url;
//        this.FileDir = FileDir;
//    }
//
//    @Override
//    public void run(){
//        // use the hash of url string as filename
//        // download image from web and copy to storage device
//        try {
//            // Our filename is simply just a hash of the url string
//            String filename = String.valueOf(url.hashCode());
//            File file = new File(FileDir, filename);
////            URL imageURL = new URL(url);
////            // We create two file streams and copy from source stream to destination stream
////            InputStream is = imageURL.openConnection().getInputStream();
//            OutputStream os = new FileOutputStream(file);
//            return os;
//        } catch (Exception e){
//            Log.e("CS205:", e.getMessage());
//        }
//    }
//
////    public void copyStream(InputStream is, OutputStream os) {
////        final int buffer_size=1024;
////        try {
////            byte[] bytes = new byte[buffer_size];
////            while (true) {
////                int count = is.read(bytes, 0, buffer_size);
////                if(count == -1)
////                    break;
////                os.write(bytes, 0, count);
////            }
////        }
////        catch(Exception e){
////            Log.e("CS205:", e.getMessage());
////        }
////    }
//}
