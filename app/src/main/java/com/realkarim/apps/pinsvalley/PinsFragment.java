package com.realkarim.apps.pinsvalley;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Karim Mostafa on 1/28/17.
 */

public class PinsFragment extends Fragment implements PinsContract.View {

    Context context;

    PinsPresenter pinsPresenter;

    @BindView(R.id.pins_recycler_view)
    RecyclerView pinsRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;
    PinsRecyclerViewAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        pinsPresenter = new PinsPresenter(context, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pins, container, false);
        ButterKnife.bind(this, view);

        // get screen width to calculate number of columns
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(context, pxToDp(width)/200);
        pinsRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new PinsRecyclerViewAdapter(context);
        pinsRecyclerView.setAdapter(mAdapter);

        pinsPresenter.updateList();

        return view;
    }

    @Override
    public void receiveListUpdate(ArrayList arrayList) {
        mAdapter.updateList(arrayList);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
