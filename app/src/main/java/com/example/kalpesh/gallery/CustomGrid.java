package com.example.kalpesh.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by omsai on 4/28/2017.
 */

public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> imageArrary;


    public CustomGrid(Context con, ArrayList<String> image) {
        mContext = con;
         this.imageArrary = image;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageArrary.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return imageArrary.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);

            grid = inflater.inflate(R.layout.grid_single, null);

            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);

            Glide.with(mContext).load("file://"+imageArrary.get(position)).placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_error_icon).override(160,160).crossFade().centerCrop().dontAnimate()
                    .skipMemoryCache(true).into(imageView);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}