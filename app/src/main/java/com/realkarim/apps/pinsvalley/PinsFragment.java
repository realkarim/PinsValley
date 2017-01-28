package com.realkarim.apps.pinsvalley;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Karim Mostafa on 1/28/17.
 */

public class PinsFragment extends Fragment implements PinsContract.View {

    Context context;

    @BindView(R.id.pins_recycler_view)
    RecyclerView pinsRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;
    PinsRecyclerViewAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pins, container, false);
        ButterKnife.bind(this, view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        pinsRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new PinsRecyclerViewAdapter(context);
        pinsRecyclerView.setAdapter(mAdapter);

        return view;
    }

}
