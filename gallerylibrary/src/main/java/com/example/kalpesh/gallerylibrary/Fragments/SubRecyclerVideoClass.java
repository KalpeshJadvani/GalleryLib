package com.example.kalpesh.gallerylibrary.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.kalpesh.gallerylibrary.Adapters.MymediaAdapter;
import com.example.kalpesh.gallerylibrary.Adapters.videoAdapter;
import com.example.kalpesh.gallerylibrary.MyGallery;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by omsai on 4/28/2017.
 */

public class SubRecyclerVideoClass {
    public static int SubPhotoLayoutWidth;
    RecyclerView sub_recycler;
    Context mContext;
    private videoAdapter mySub_Adapter;
    private final String[] projection2 = new String[]{MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DATA };
    private List<String> mediaList=new ArrayList<>();
    public static List<Boolean> sub_selected=new ArrayList<>();
    public static ArrayList<String> videosSelected=new ArrayList<>();
    public List<String> bucketNames;
    public Toolbar toolbar;


    public SubRecyclerVideoClass(RecyclerView sub_recy, Activity context, List<String> Names, final Toolbar tool){
        this.sub_recycler=sub_recy;
        this.mContext=context;
        this.toolbar=tool;
        sub_selected.clear();
        mediaList.clear();

        this.bucketNames=Names;

        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(context,2);
        sub_recycler.setLayoutManager(mLayoutManager2);
        //sub_recycler.addItemDecoration(new GridSpacingItemDecoration(dpToPx(3)));
        sub_recycler.setItemAnimator(new DefaultItemAnimator());


        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        SubPhotoLayoutWidth = width/3;

        sub_recycler.addOnItemTouchListener(new SubRecyclerTouchListener(mContext, sub_recycler, new SubClickListener() {
            @Override
            public void onClick(View view, int position) {


                    if(!sub_selected.get(position).equals(true)){
                        videosSelected.add(mediaList.get(position));
                    }else {
                        if(videosSelected.indexOf(mediaList.get(position))!= -1) {
                            videosSelected.remove(videosSelected.indexOf(mediaList.get(position)));
                        }
                    }

                    sub_selected.set(position,!sub_selected.get(position));

                    MyGallery.selectionTitle = videosSelected.size();

                    if (videosSelected.size() != 0) {
                        toolbar.setTitle(String.valueOf(videosSelected.size()));
                    } else {
                        toolbar.setTitle(MyGallery.title);
                    }


                mySub_Adapter.notifyItemChanged(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




    }

    public interface SubClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public static class SubRecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private SubClickListener clickListener;

        public SubRecyclerTouchListener(Context context, final RecyclerView sub_recycler, final SubClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = sub_recycler.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, sub_recycler.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
    public void setSubRecyclerView(int position) {

        getPictures(bucketNames.get(position));


        for(int i=0;i<sub_selected.size();i++){
            if(videosSelected.contains(mediaList.get(i))){
                sub_selected.set(i,true);
            }else {
                sub_selected.set(i,false);
            }
        }

        mySub_Adapter = new videoAdapter(mediaList, sub_selected, mContext, SubPhotoLayoutWidth);
        sub_recycler.setAdapter(mySub_Adapter);

    }



    public void getPictures(String bucket){

        sub_selected.clear();

        Cursor cursor = mContext.getContentResolver()
                .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection2,
                        MediaStore.Video.Media.BUCKET_DISPLAY_NAME+" =?",new String[]{bucket},MediaStore.Video.Media.DATE_ADDED);
        ArrayList<String> imagesTEMP = new ArrayList<>(cursor.getCount());

        HashSet<String> albumSet = new HashSet<>();
        File file;
        if (cursor.moveToLast()) {
            do {
                if (Thread.interrupted()) {
                    return;
                }
                String path = cursor.getString(cursor.getColumnIndex(projection2[1]));
                file = new File(path);
                if (file.exists() && !albumSet.contains(path)) {

                        String filename = path.substring(path.lastIndexOf("/")+1);
                        String extension = FilenameUtils.getExtension(filename);

                        if(extension.equals("gif") || extension.equals("GIF")){


                        }else {
                            imagesTEMP.add(path);
                            albumSet.add(path);
                            sub_selected.add(false);

                        }

                }

            } while (cursor.moveToPrevious());
        }
        cursor.close();
        if (imagesTEMP == null) {
            imagesTEMP = new ArrayList<>();
        }

        mediaList.clear();
        mediaList.addAll(imagesTEMP);


    }
}
