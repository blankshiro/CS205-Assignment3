package com.example.cs205t8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ListAdapter listAdapter;
    private Saver saver;
    private Cacher cacher;
    private TextView textView;
    private Handler handler;
    private long cacheLimit = 5L * 1024 * 1024;

    @Override
    // Entry point for app
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout for main activity
        setContentView(R.layout.activity_main);

        handler = new Handler();
        cacher = new Cacher(cacheLimit);

        Button b1 = findViewById(R.id.LoadButton);
        // Attach listener to button
        b1.setOnClickListener(webListener);
        Button b2 = findViewById(R.id.ClearCacheButton);
        b2.setOnClickListener(cacheListener);
        Button b3 = findViewById(R.id.DeleteButton);
        b3.setOnClickListener(saveListener);

        textView = findViewById(R.id.textView);

        list = findViewById(R.id.list);
        listAdapter = new ListAdapter(MainActivity.this, URLs, cacher);

    }

    public void popUpMessage(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void sendMessageUI(String text){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }

    public View.OnClickListener webListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            popUpMessage("Download from Web");
            // When button is clicked, attach a ListAdapter to a ListView object
            list.setAdapter(listAdapter);
        }
    };

    public View.OnClickListener cacheListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            popUpMessage("Cache to memory");
            final ProgressDialog pd = ProgressDialog.show(MainActivity.this,"Caching",
                    "Caching images to memory...",true, false);
            // When clicked, a new thread is started which loops through all the urls and cache whenever possible
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (String url:URLs){
                        cacher.cacheImage(url);
                    }
                    // After caching is done, send a message to UI TextView object
                    sendMessageUI(cacher.getCurrSize()/1024 + " KB used out of available cache of "
                            + cacher.getLimit()/1024 + " KB" );
                    pd.dismiss();
                }
            }).start();
        }
    };

    public View.OnClickListener saveListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            final ProgressDialog pd = ProgressDialog.show(MainActivity.this,"Saving",
                    "Saving files to device storage...",true, false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    saver = new Saver(getExternalFilesDir(null)+ "/allfiles");
                    for (String url:URLs){
                        saver.SaveImage(url);
                    }
                    sendMessageUI("Images saved");
                    pd.dismiss();
                }
            }).start();
        }
    };

    public View.OnClickListener funListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            popUpMessage("testing");
            cacher.clear();
        }
    };

    @Override
    public void onDestroy(){
        list.setAdapter(null);
        super.onDestroy();
    }

    private String[] URLs = {
            "https://i.ibb.co/8PpYkH4/airplane.png",
            "https://i.ibb.co/GtvS9Kv/arctichare.png",
            "https://i.ibb.co/R68YhSN/baboon.png",
            "https://i.ibb.co/s1sgt1h/barbara.png",
            "https://i.ibb.co/X53Gznv/boat.png",
            "https://i.ibb.co/xG3R3p1/cat.png",
            "https://i.ibb.co/dMs8Kvx/fruits.png",
            "https://i.ibb.co/M2QzSFg/frymire.png",
            "https://i.ibb.co/dMZ2N3N/girl.png",
            "https://i.ibb.co/XCrkhc3/goldhill.png",
            "https://i.ibb.co/cv0Jm1n/lena.png",
            "https://i.ibb.co/DVM6Cvq/monarch.png",
            "https://i.ibb.co/PtWRX6x/mountain.png",
            "https://i.ibb.co/ph6V8bd/peppers.png",
            "https://i.ibb.co/RDhmNMh/pool.png",
            "https://i.ibb.co/z6ZD7Ny/sails.png",
            "https://i.ibb.co/gyMyzcW/serrano.png",
            "https://i.ibb.co/5Y1ZFYj/tulips.png",
            "https://i.ibb.co/x2khkdG/watch.png",
            "https://i.ibb.co/pnGQk92/zelda.png"
    };
}