package com.inc.bokoch.and.tatrenko.bookshop.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.inc.bokoch.and.tatrenko.bookshop.Adapters.DetailFragmentAdapter;
import com.inc.bokoch.and.tatrenko.bookshop.Model.Book;
import com.inc.bokoch.and.tatrenko.bookshop.R;

import java.util.ArrayList;
import java.util.List;


public class DetailBookFragment extends Fragment {
    public static final String TAG = DetailBookFragment.class.getSimpleName();

    public static final String POSITION_BOOK = "position_image";
    public static final String BOOK_TAG = "photo";

    private ViewPager mViewPager;
    private List<Book> mBooks;
    private DetailFragmentAdapter mAdapter;
    private int mPosition;

    public static DetailBookFragment newInstance(int position, List<Book> books) {
        Bundle bundle = new Bundle();

        bundle.putInt(POSITION_BOOK, position);
        bundle.putParcelableArrayList(BOOK_TAG, (ArrayList<? extends Parcelable>) books);
        DetailBookFragment fragment = new DetailBookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBooks = new ArrayList<>();
        mPosition = getArguments().getInt(POSITION_BOOK);
        mBooks =  getArguments().getParcelableArrayList(BOOK_TAG);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_book_pager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_detail_view_pager);
        mPosition = mPosition < 0 ? 0 : mPosition;

        getActivity().setTitle(mBooks.get(mPosition).getTitle());

        mAdapter = new DetailFragmentAdapter(getActivity().getSupportFragmentManager(), mBooks);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mPosition);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                //Для каждой книги свое имя.
                getActivity().setTitle(mBooks.get(position).getTitle());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }

}
