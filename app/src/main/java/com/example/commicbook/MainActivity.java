package com.example.commicbook;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commicbook.Adapter.ImageSlider;
import com.example.commicbook.Adapter.MyComicAdapter;
import com.example.commicbook.Common.Common;
import com.example.commicbook.Model.Banner;
import com.example.commicbook.Model.Comic;
import com.example.commicbook.Retrofit.ICommicApi;
import com.example.commicbook.Service.PicassoImageLoadingService;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity {
    TextView text_Comic;
    Slider slider;
    RecyclerView recyclerView;
    ICommicApi iCommicApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initApi
        iCommicApi = Common.getApi();
        slider = findViewById(R.id.imageslider);
        recyclerView = findViewById(R.id.rv_list);
        text_Comic=findViewById(R.id.comic_count);
        Slider.init(new PicassoImageLoadingService());

        fetchBanner();
        fetchComicList();

    }

    private void fetchComicList() {
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).setMessage("Please wait....").build();
        dialog.show();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        compositeDisposable.add(iCommicApi.getComic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comics) throws Exception {
                        dialog.dismiss();
                        recyclerView.setAdapter(new MyComicAdapter(getApplicationContext(), comics));
                        text_Comic.setText(new StringBuilder("NEW COMIC (")
                        .append(comics.size()));
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), comics.size(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error while loading comic list", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void fetchBanner() {
        compositeDisposable.add(iCommicApi.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Banner>>() {
                    @Override
                    public void accept(List<Banner> banners) throws Exception {
                        slider.setAdapter(new ImageSlider(banners));


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(), "Error while image loading", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", throwable.getMessage());
                    }
                }));
    }
}
