package com.example.android.popularmovies.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

// Fix for ExpandableListViews scroll taking over the scrollView scroll.
// From https://stackoverflow.com/a/37605908/9426223
// Thank you V-rund Puro-hit

public class NonScrollableExpandableListView extends ExpandableListView {
    public NonScrollableExpandableListView(Context context) {
        super(context);
    }

    public NonScrollableExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollableExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
