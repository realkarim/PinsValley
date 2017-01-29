package com.realkarim.apps.pinsvalley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.realkarim.apps.pinsvalley.models.Pin;
import com.realkarim.apps.xfetcher.BitmapFetcher;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 1/28/17.
 */

public class PinsRecyclerViewAdapter extends RecyclerView.Adapter<PinsRecyclerViewAdapter.ViewHolder> {

    private Context context = null;
    private ArrayList<Pin> pins;
    String TAG = this.getClass().getName();

    PinsRecyclerViewAdapter(Context context) {
        this.context = context;
        pins = new ArrayList<>();
    }

    public void updateList(ArrayList<Pin> pins) {
        this.pins.clear();
        this.pins = pins;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pins_recylerview_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.loading);
        Pin currentPin = pins.get(position);

        String url = currentPin.getUrls().getRegular();
        BitmapFetcher bitmapFetcher = new BitmapFetcher(context) {
            @Override
            public void onResponse(Bitmap response) {
                holder.image.setImageBitmap(response);
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "Error fetching image: " + error);
                holder.image.setImageResource(R.drawable.photo_not_available);
            }
        };

        bitmapFetcher.fetchFromURL(url);

        String username = currentPin.getUser().getUsername();
        Integer likesCount = currentPin.getLikes();

        holder.user.setText("by: " + username);
        holder.likes.setText("" + likesCount);

    }

    @Override
    public int getItemCount() {
        return pins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView user;
        public TextView likes;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            user = (TextView) v.findViewById(R.id.user);
            likes = (TextView) v.findViewById(R.id.likes);
        }
    }
}
