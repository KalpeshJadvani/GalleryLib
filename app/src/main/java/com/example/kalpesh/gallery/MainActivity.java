package com.example.kalpesh.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;


import com.example.kalpesh.gallerylibrary.MyGallery;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final int OPEN_IMAGE_PICKER = 1;  // The request code
    static final int OPEN_VIDEO_PICKER = 2;  // The request code
    GridView ImageCollection,VideoCollection;
    CustomGrid adapter;

    ArrayList<String> image;

    ArrayList<String> videoPath;

    TextView OpenVideo,OpenImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageCollection = (GridView)findViewById(R.id.imagegridview);
        VideoCollection = (GridView)findViewById(R.id.videogridview);

        OpenVideo = (TextView)findViewById(R.id.opengallery);
        OpenImages = (TextView)findViewById(R.id.openvideogallery);

        OpenVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImageGallery();

            }
        });

        OpenImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openVideoGallery();

            }
        });

    }

    public void openImageGallery() {
        Intent intent= new Intent(MainActivity.this, MyGallery.class);
        intent.putExtra("title","Select Images");
        intent.putExtra("type","image");
        startActivityForResult(intent,OPEN_IMAGE_PICKER);
    }

    public void openVideoGallery() {
        Intent intent= new Intent(MainActivity.this, MyGallery.class);
        intent.putExtra("title","Select Video");

        /*****  If you wanto pick All file so you haev to pass parameter this ******/
         // intent.putExtra("type","videoall");



        /*****  If you wanto pick one file so you haev to pass parameter this ******/
        intent.putExtra("type","video1");


        startActivityForResult(intent,OPEN_VIDEO_PICKER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        if (requestCode == OPEN_IMAGE_PICKER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {

                ImageCollection.setVisibility(View.VISIBLE);
                OpenImages.setVisibility(View.INVISIBLE);
                OpenVideo.setVisibility(View.VISIBLE);
                image = data.getStringArrayListExtra("image");
                adapter= new CustomGrid(MainActivity.this,image);
                ImageCollection.setAdapter(adapter);
            }
        }


        if (requestCode == OPEN_VIDEO_PICKER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {



              /*****  If you wanto pick All file so you can do like this ******/
                //videoPath = data.getStringArrayListExtra("videoall");




                /*****  If you wanto pick one file so you can do like this ******/
                 videoPath = data.getStringArrayListExtra("video1");
                //String video1Path =  videoPath.get(0);



                VideoCollection.setVisibility(View.VISIBLE);
                OpenImages.setVisibility(View.VISIBLE);
                OpenVideo.setVisibility(View.INVISIBLE);
                adapter= new CustomGrid(MainActivity.this,videoPath);
                VideoCollection.setAdapter(adapter);


            }
        }

    }


    @Override
    public void onBackPressed() {
        if(VideoCollection.getVisibility()==View.VISIBLE || ImageCollection.getVisibility()==View.VISIBLE){

            VideoCollection.setVisibility(View.INVISIBLE);
            ImageCollection.setVisibility(View.INVISIBLE);

            OpenVideo.setVisibility(View.VISIBLE);
            OpenImages.setVisibility(View.VISIBLE);

        }else {
            super.onBackPressed();
        }

    }
}
