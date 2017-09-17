package com.mursitaffandi.mursitaffandi_baking.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mursitaffandi.mursitaffandi_baking.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFoodActivityFragment extends Fragment {

    public DetailFoodActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_food, container, false);
    }
}
