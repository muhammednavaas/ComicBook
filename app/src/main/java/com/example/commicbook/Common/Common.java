package com.example.commicbook.Common;

import com.example.commicbook.Model.Chapter;
import com.example.commicbook.Model.Comic;
import com.example.commicbook.Retrofit.ICommicApi;
import com.example.commicbook.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static Comic selected_comic;
    public static Chapter selected_chapeter;
    public static int chapter_index = -1;
    public static List<Chapter> chapterList = new ArrayList<>();

    public static ICommicApi getApi() {
        return RetrofitClient.getInstance().create(ICommicApi.class);
    }

    public static String formatingString(String name) {
        //If chapter is too long ,just substring
        StringBuilder finalResult = new StringBuilder(name.length() > 15 ? name.substring(0, 15) + "..." : name);
        return finalResult.toString();
    }
}
