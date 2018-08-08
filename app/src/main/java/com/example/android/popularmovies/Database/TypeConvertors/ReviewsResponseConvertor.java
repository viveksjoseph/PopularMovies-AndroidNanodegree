package com.example.android.popularmovies.Database.TypeConvertors;

import android.arch.persistence.room.TypeConverter;

import com.example.android.popularmovies.Data.Reviews.ReviewResponse;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.google.gson.Gson;

public class ReviewsResponseConvertor {

    @TypeConverter
    public static ReviewResponse stringToReviewsList(String jsonData) {
        if (jsonData == null) {
            return null;
        }

        return JsonUtils.parseReviewResponseJson(jsonData);
    }

    @TypeConverter
    public static String ReviewsListToString(ReviewResponse reviewResponse) {
        Gson gson = new Gson();
        return gson.toJson(reviewResponse);
    }
}
