package com.realkarim.apps.pinsvalley;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.realkarim.apps.xfetcher.BitmapFetcher;
import com.realkarim.apps.xfetcher.StringFetcher;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;

public class PinsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new PinsFragment())
                .commit();
    }
}
