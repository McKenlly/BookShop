package com.inc.bokoch.and.tatrenko.bookshop.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.bokoch.and.tatrenko.bookshop.R;


public class MAILogoFragment extends Fragment {

    public static MAILogoFragment newInstance() {
        return new MAILogoFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mai_logo_fragment, container, false);
    }

}
