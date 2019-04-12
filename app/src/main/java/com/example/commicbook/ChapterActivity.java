package com.example.commicbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.commicbook.Adapter.MyChapterAdapter;
import com.example.commicbook.Adapter.MyComicAdapter;
import com.example.commicbook.Common.Common;
import com.example.commicbook.Model.Chapter;
import com.example.commicbook.Retrofit.ICommicApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChapterActivity extends AppCompatActivity {
    ICommicApi iCommicApi;
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView text_ChapterCount;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selected_comic.getName());
        //setIocn for Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iCommicApi = Common.getApi();
        recyclerView = findViewById(R.id.rv_chapter);
        text_ChapterCount = findViewById(R.id.text_chapter);

        fecthChapterList(Common.selected_comic.getID());
    }

    private void fecthChapterList(int comicid) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));

        compositeDisposable.add(iCommicApi.getChapter(comicid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Chapter>>() {
                    @Override
                    public void accept(List<Chapter> chapters) throws Exception {
                        Common.chapterList = chapters;// Save to back ,next
                        recyclerView.setAdapter(new MyChapterAdapter(getApplicationContext(), chapters));
                        text_ChapterCount.setText(new StringBuilder("CHAPTER(")
                        .append(chapters.size())
                        .append(")"));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
}
