package com.example.cs205t8;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
    private Activity activity;
    private String[] URLs;
    private static LayoutInflater inflater = null;
    private Loader loader;

    public ListAdapter(Activity activity, String[] URLs, Cacher cacher) {
        this.activity = activity;
        this.URLs = URLs;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.loader = new Loader(cacher);
    }

    @Override
    public int getCount(){
        return URLs.length;
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    // This method returns the View object for each row of the list
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if (convertView == null){
            view = inflater.inflate(R.layout.item, null);
        }

        // Set the text value in the item View
        TextView textView = view.findViewById(R.id.text);
        // LoadText method dictates what text to show
        loader.LoadText(URLs[position], textView, position);

        // set the bitmap value in the image View
        ImageView image = view.findViewById(R.id.image);
        // LoadImage method detects what image to load
        loader.LoadImage(URLs[position], image);
        return view;
    }
}
