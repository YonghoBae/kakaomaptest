package com.example.km1;

import net.daum.mf.map.api.MapView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assuming you have a layout 'activity_main' with a FrameLayout or similar ViewGroup
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup container = findViewById(R.id.map_view);

        // Inflate the layout for the fragment
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) rootView.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // Add the fragment view to the container
        container.addView(rootView);
    }
}
