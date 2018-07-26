package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.Data.Reviews.ReviewDetails;
import com.example.android.popularmovies.Data.Videos.VideoDetails;
import com.example.android.popularmovies.R;

import java.util.HashMap;
import java.util.List;


public class MovieExpandableListAdapter extends BaseExpandableListAdapter {

    public enum ExpandableListType {
        EXPANDABLE_VIDEO_LIST,
        EXPANDABLE_REVIEW_LIST;
    }

    private Context mContext = null;
    private List<String> mHeaderList = null;
    private ExpandableListType mListType;
    private HashMap<String, List<?>> mContentMap = null;

    public MovieExpandableListAdapter(Context context, ExpandableListType listType, List<String> headerList, HashMap<String, List<?>> contentMap) {
        mContext = context;
        mListType = listType;
        mHeaderList = headerList;
        mContentMap = contentMap;
    }

    @Override
    public int getGroupCount() {
        return mHeaderList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mContentMap.get(mHeaderList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mHeaderList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        // i = Header/Group index
        // i1 = Child index
        return mContentMap.get(mHeaderList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        final String headerTitle = (String) getGroup(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.detail_expandable_headings, null);
        }

        TextView headerTv = (TextView) view.findViewById(R.id.exp_headings_tv);
        headerTv.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (mListType == ExpandableListType.EXPANDABLE_VIDEO_LIST) {
            VideoDetails videoDetails = (VideoDetails) getChild(i, i1);

            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.detail_expandable_videos, null);
            }

            TextView videoLinkText = (TextView) view.findViewById(R.id.exp_videos_tv);
            videoLinkText.setText(videoDetails.getVideoName());

            return view;

        } else if (mListType == ExpandableListType.EXPANDABLE_REVIEW_LIST) {
            ReviewDetails reviewDetails = (ReviewDetails) getChild(i, i1);

            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.detail_expandable_reviews, null);
            }

            TextView reviewAuthorText = (TextView) view.findViewById(R.id.exp_review_author_tv);
            reviewAuthorText.setText(reviewDetails.getReviewAuthor());

            TextView reviewText = (TextView) view.findViewById(R.id.exp_review_tv);
            reviewText.setText(reviewDetails.getReviewContent());

            return view;
        }
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        if (mListType == ExpandableListType.EXPANDABLE_VIDEO_LIST) {

            VideoDetails videoDetails = (VideoDetails) getChild(i, i1);
            return !videoDetails.getVideoKey().isEmpty();

        } else if (mListType == ExpandableListType.EXPANDABLE_REVIEW_LIST) {

            ReviewDetails reviewDetails = (ReviewDetails) getChild(i, i1);
            return !reviewDetails.getReviewUrl().isEmpty();
        }

        return true;
    }
}
