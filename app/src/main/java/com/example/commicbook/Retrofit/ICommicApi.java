package com.example.commicbook.Retrofit;

import com.example.commicbook.Model.Banner;
import com.example.commicbook.Model.Chapter;
import com.example.commicbook.Model.Comic;
import com.example.commicbook.Model.Link;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICommicApi {
    //Get All Banner
    @GET("banner")
    Observable<List<Banner>> getBanner();

    @GET("comic")
    Observable<List<Comic>> getComic();

    @GET("chapter/{comicid}")
    Observable<List<Chapter>> getChapter(@Path("comicid") int comicid);

    @GET("links/{chapterid}")
    Observable<List<Link>> getImageList(@Path("chapterid") int chapterid);
}
