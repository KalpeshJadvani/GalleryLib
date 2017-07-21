package com.example.kalpesh.gallerylibrary.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kalpesh.gallerylibrary.R;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by omsai on 4/28/2017.
 */

public class PhotoBucketsAdapter extends RecyclerView.Adapter<PhotoBucketsAdapter.MyViewHolder>{

    private List<String> bucketNames,bitmapList;
    private Activity context;
    int selectedPosition=-1;
    public int[] RandArry=new int[]{1,2,3,4,5,6,7,8,9,10};
    private Picasso picasso;
    private LruCache picassoLruCache;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public LinearLayout mainlayout;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail=(ImageView) view.findViewById(R.id.image);
            mainlayout = (LinearLayout) view.findViewById(R.id.main_layout);
        }
    }


    public PhotoBucketsAdapter(List<String> bucketNames, List<String> bitmapList, Activity context) {
        this.bucketNames = bucketNames;
        this.bitmapList = bitmapList;
        this.context = context;
        picassoLruCache = new LruCache(context);

       // Set cache
        picasso = new Picasso.Builder(context) //
                .memoryCache(picassoLruCache) //
                .build();
    }


    public void RandomFunction(){

        Random rand = new Random();
        int selector = rand.nextInt(RandArry.length);
        if (selector == 5)
        Clear_GlideCaches();

    }




    public void Clear_GlideCaches() {

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                picassoCache.clear();
//
//            }
//        }, 0);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                picassoLruCache.clear();
                Log.i("my","get call here ");

            }
        });
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        bucketNames.get(position);
        holder.title.setText(bucketNames.get(position));

//        Glide.with(context).load("file://"+bitmapList.get(position)).override(300,300).centerCrop().into(holder.thumbnail);
        picasso.with(context)
                .load("file://"+bitmapList.get(position))
                 .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_error_icon)
                .resize(300, 300).centerCrop()
                .into(holder.thumbnail);

        if(selectedPosition==position){

            holder.mainlayout.setAlpha(1.0f);

            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        else{
            holder.mainlayout.setAlpha(0.5f);
            holder.thumbnail.setBackgroundColor(Color.TRANSPARENT);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();

            }
        });



    }

    @Override
    public int getItemCount() {
        return bucketNames.size();
    }


}
