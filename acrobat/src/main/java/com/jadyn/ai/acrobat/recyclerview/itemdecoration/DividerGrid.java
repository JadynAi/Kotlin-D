package com.jadyn.ai.acrobat.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class DividerGrid extends RecyclerView.ItemDecoration {

    private int mSpacing;
    private boolean mIncludeEdge;

    private int mSpanCount;

    public DividerGrid(int spacing, boolean includeEdge) {
        this.mSpacing = spacing;
        this.mIncludeEdge = includeEdge;
    }

    public DividerGrid(int spacing, boolean includeEdge, int spanCount) {
        this(spacing, includeEdge);
        this.mSpanCount = spanCount;
    }

    private int getSpanCount(RecyclerView parent) {
        if (mSpanCount > 0) {
            return mSpanCount;
        }
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int column = position % spanCount;
        if (mIncludeEdge) {
            outRect.left = mSpacing - column * mSpacing / spanCount;
            outRect.right = (column + 1) * mSpacing / spanCount;
            if (position < spanCount) {
                outRect.top = mSpacing;
            }
            outRect.bottom = mSpacing;
        } else {
            outRect.left = column * mSpacing / spanCount;
            outRect.right = mSpacing - (column + 1) * mSpacing / spanCount;
            outRect.bottom = mSpacing;
        }
    }
}