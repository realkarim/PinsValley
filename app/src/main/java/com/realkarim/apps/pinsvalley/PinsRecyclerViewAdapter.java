package com.realkarim.apps.pinsvalley;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.realkarim.apps.xfetcher.BitmapFetcher;
/**
 * Created by Karim Mostafa on 1/28/17.
 */

public class PinsRecyclerViewAdapter extends RecyclerView.Adapter<PinsRecyclerViewAdapter.ViewHolder>{

    Context context = null;


    PinsRecyclerViewAdapter(Context context){
        this.context = context;
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
        String url = "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec";

        BitmapFetcher bitmapFetcher = new BitmapFetcher(context) {
            @Override
            public void onResponse(Bitmap response) {
                holder.image.setImageBitmap(response);
            }

            @Override
            public void onError(String error) {
                Log.e("test", error);
            }
        };

        bitmapFetcher.fetchFromURL(url);

    }

    @Override
    public int getItemCount() {
        return 10;
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
