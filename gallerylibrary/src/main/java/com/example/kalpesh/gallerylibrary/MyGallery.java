package com.example.kalpesh.gallerylibrary;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalpesh.gallerylibrary.Fragments.PhotoFragment;
import com.example.kalpesh.gallerylibrary.Fragments.SubRecyclerOneVideoClass;
import com.example.kalpesh.gallerylibrary.Fragments.SubRecyclerVideoClass;
import com.example.kalpesh.gallerylibrary.Fragments.SubRecyclerViewClass;
import com.example.kalpesh.gallerylibrary.Fragments.VideoFragment;
import com.example.kalpesh.gallerylibrary.Fragments.VideoOneFragment;

public class MyGallery extends Activity {
    public static int selectionTitle;
    public static String title;
    public static String type;
    public FrameLayout Contener;
    public static  Toolbar toolbar;
    public RelativeLayout NextText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_gallery);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent=getIntent();
        title=intent.getExtras().getString("title");
        type= intent.getExtras().getString("type").toLowerCase();
        toolbar.setTitle(title);
        selectionTitle=0;
        System.gc();
        Contener = (FrameLayout)findViewById(R.id.contenar);

        if(weHavePermissionToReadExternalStorage() || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP){

            setupViewPager(R.id.contenar);

            NextText = (RelativeLayout)findViewById(R.id.rightnext);

            NextText.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            NextText.setBackgroundColor(Color.parseColor("#372D0000"));

                            break;

                        case MotionEvent.ACTION_UP:
                            // Your action here on button click

                            returnResult();

                            NextText.setBackgroundColor(Color.TRANSPARENT);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            NextText.setBackgroundColor(Color.TRANSPARENT);

                            break;

                    }
                    return true;
                }
            });





            SubRecyclerViewClass.sub_selected.clear();
            SubRecyclerViewClass.imagesSelected.clear();
            SubRecyclerViewClass.imagesSelected.clear();
            SubRecyclerVideoClass.videosSelected.clear();
            SubRecyclerVideoClass.sub_selected.clear();

        }else {

            requestREAD_EXTERNALPermissionFirst();
        }



    }

    private void requestREAD_EXTERNALPermissionFirst() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "We need permission so you can read .", Toast.LENGTH_LONG).
                    show();
            requestForResultExternalStoragePermission();
        } else {
            requestForResultExternalStoragePermission();
        }
    }

    private void requestForResultExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 112);

    }

    //read external storage

    private boolean weHavePermissionToReadExternalStorage()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 112 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            onCreate(new Bundle());

        }
    }

    @Override
    public void onBackPressed() {

        SubRecyclerViewClass.sub_selected.clear();
        SubRecyclerViewClass.imagesSelected.clear();
        SubRecyclerViewClass.imagesSelected.clear();
        SubRecyclerVideoClass.videosSelected.clear();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    //This method set up the tab view for images and videos
    private void setupViewPager(int viewPager) {

        if (type.equals("image")){
            getFragmentManager().beginTransaction().replace(viewPager, new PhotoFragment()).commit();
        }

        if (type.equals("videoall")){
            getFragmentManager().beginTransaction().replace(viewPager, new VideoFragment()).commit();
        }

        if (type.equals("video1")){
            getFragmentManager().beginTransaction().replace(viewPager, new VideoOneFragment()).commit();

        }

    }


    private void returnResult() {

        Intent returnIntent = new Intent();
        if (type.equals("image")){
            returnIntent.putStringArrayListExtra("image", SubRecyclerViewClass.imagesSelected);
        }if (type.equals("videoall")){
            returnIntent.putStringArrayListExtra("videoall", SubRecyclerVideoClass.videosSelected);
        }if (type.equals("video1")){
            returnIntent.putStringArrayListExtra("video1", SubRecyclerOneVideoClass.videosSelected);
        }
        setResult(RESULT_OK, returnIntent);
        finish();
    }


}
