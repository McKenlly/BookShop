package com.inc.bokoch.and.tatrenko.bookshop.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.bokoch.and.tatrenko.bookshop.Adapters.RecyclerViewBookAdapter;
import com.inc.bokoch.and.tatrenko.bookshop.Model.Book;
import com.inc.bokoch.and.tatrenko.bookshop.Model.GenreEnum;
import com.inc.bokoch.and.tatrenko.bookshop.Network.GetAsyncTask;
import com.inc.bokoch.and.tatrenko.bookshop.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SuppressLint("ValidFragment")
public class RecyclerViewBookFragment extends Fragment {

    public static final String TAG = RecyclerViewBookFragment.class.getSimpleName();
    public static final String GENRE_TAG = "genre";

    private RecyclerViewBookAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private byte mChoice;


    public static RecyclerViewBookFragment newInstance(byte choice) {
        Bundle bundle = new Bundle();
        bundle.putByte(GENRE_TAG, choice);
        RecyclerViewBookFragment fragment = new RecyclerViewBookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mChoice = getArguments().getByte(GENRE_TAG);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_view, container, false);
        Log.e(TAG, "onCreateView");

        mRecyclerView =(RecyclerView) v.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.fbutton_color_asbestos,
                                                R.color.fbutton_color_carrot, R.color.fbutton_color_concrete);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //setupAdapter();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
        String jsonText;
        GetAsyncTask task = new GetAsyncTask();
        task.execute(getGenreEnum(mChoice));
        List<Book> books = new ArrayList<Book>();
        try {

            books = task.get();
            if (books != null) {
                mAdapter = new RecyclerViewBookAdapter(this, books);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);

        return v;

    }

    public GenreEnum getGenreEnum(int i) {
        switch (i) {
            case 0 : return GenreEnum.Action;
            case 1 : return GenreEnum.Adventure;
            case 2 : return GenreEnum.Fantasy;
            case 3 : return GenreEnum.Drama;
            default: return GenreEnum.Action;
        }
    }


}
