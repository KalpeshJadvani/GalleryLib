package com.example.kalpesh.gallerylibrary.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.kalpesh.gallerylibrary.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by omsai on 4/28/2017.
 */

public class MymediaAdapter extends RecyclerView.Adapter<MymediaAdapter.MyViewHolder>{

    private List<String> bitmapList;
    private List<Boolean> selected;
    private Context context;
    private int SubPhotoLayoutWidth;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail,check;
        public FrameLayout subphotoframe;

        public MyViewHolder(View view) {
            super(view);
            thumbnail=(ImageView) view.findViewById(R.id.image);
            check=(ImageView) view.findViewById(R.id.image2);
            subphotoframe = (FrameLayout) view.findViewById(R.id.subphotoframe);
            subphotoframe.getLayoutParams().height=SubPhotoLayoutWidth;
            subphotoframe.getLayoutParams().width=SubPhotoLayoutWidth;

        }
    }

    public MymediaAdapter(List<String> bitmapList, List<Boolean> selected, Context context,int width) {
        this.bitmapList = bitmapList;
        this.context=context;
        this.selected=selected;
        this.SubPhotoLayoutWidth=width;
    }


    @Override
    public MymediaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_media_item, parent, false);

        return new MymediaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MymediaAdapter.MyViewHolder holder, int position) {
        Picasso.with(context)
                .load("file://"+bitmapList.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_error_icon)
                .resize(160, 160).centerCrop()
                .into(holder.thumbnail);

//        Glide.with(context).load("file://"+bitmapList.get(position)).override(153,160).crossFade().centerCrop().dontAnimate().skipMemoryCache(true).into(holder.thumbnail);

        if(selected.get(position).equals(true)){
            holder.check.setVisibility(View.VISIBLE);
            holder.check.setAlpha(150);
        }else{
            holder.check.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }
}
