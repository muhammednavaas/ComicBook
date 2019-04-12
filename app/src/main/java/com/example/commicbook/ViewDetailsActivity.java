package com.example.commicbook;

import android.app.AlertDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commicbook.Adapter.MyViewPagerAdater;
import com.example.commicbook.Common.Common;
import com.example.commicbook.Model.Link;
import com.example.commicbook.Retrofit.ICommicApi;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewDetailsActivity extends AppCompatActivity {
    ICommicApi iCommicApi;
    MyViewPagerAdater myViewPagerAdater;
    ViewPager viewPager;
    CompositeDisposable compositeDisposablem = new CompositeDisposable();
    View back, next;
    TextView pagenumber;

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposablem.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        iCommicApi = Common.getApi();
        viewPager = findViewById(R.id.viewPager);
        back = findViewById(R.id.chapter_back);
        next = findViewById(R.id.chapter_next);
        pagenumber = findViewById(R.id.text_page_number);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.chapter_index == 0) {
                    Toast.makeText(getApplicationContext(), "Your reading first chapter", Toast.LENGTH_SHORT).show();
                } else {
                    Common.chapter_index--;
                    fetchLinks(Common.chapterList.get(Common.chapter_index).getID());
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.chapter_index == Common.chapterList.size() - 1) { //If user last chapter but press next
                    Toast.makeText(getApplicationContext(), "Your reading last chapter", Toast.LENGTH_SHORT).show();
                } else {
                    Common.chapter_index++;
                    fetchLinks(Common.chapterList.get(Common.chapter_index).getID());
                }

            }
        });
        fetchLinks(Common.selected_chapeter.getID());

    }

    private void fetchLinks(int chapter_index) {
//        final AlertDialog alertDialog= new SpotsDialog.Builder().setContext(getApplicationContext()).setMessage("Please wait...").setCancelable(false).build();
//        alertDialog.show();
        compositeDisposablem.add(iCommicApi.getImageList(chapter_index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Link>>() {
                    @Override
                    public void accept(List<Link> links) throws Exception {
                        // alertDialog.dismiss();
                        myViewPagerAdater = new MyViewPagerAdater(getApplicationContext(), links);
                        viewPager.setAdapter(myViewPagerAdater);
                        pagenumber.setText(Common.formatingString(Common.selected_chapeter.getName()));
                        Log.e("PageNumber",Common.selected_chapeter.getName());
                        //Create Book Flip Page
                        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                        bookFlipPageTransformer.setScaleAmountPercent(10f);
                        viewPager.setPageTransformer(true, bookFlipPageTransformer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "This chapter is being translating", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

}
