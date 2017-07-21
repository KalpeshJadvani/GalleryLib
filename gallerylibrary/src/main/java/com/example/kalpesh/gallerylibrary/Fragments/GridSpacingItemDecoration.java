package com.example.kalpesh.gallerylibrary.Fragments;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by omsai on 4/28/2017.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpacing;

    public GridSpacingItemDecoration(int space) {
        this.mSpacing = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
            RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpacing;
        outRect.top = mSpacing;
        outRect.right = mSpacing;
        outRect.bottom = mSpacing;
    }

}


