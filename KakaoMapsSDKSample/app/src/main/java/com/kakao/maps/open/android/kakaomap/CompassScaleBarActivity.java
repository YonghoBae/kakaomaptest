package com.kakao.maps.open.android.kakaomap;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.Compass;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.MapGravity;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.ScaleBar;


public class CompassScaleBarActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private ScaleBar scaleBar;
    private Compass compass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_scale_bar);

        setTitle("Compass, ScaleBar Demo");

        final MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;

                scaleBar = kakaoMap.getScaleBar();
                scaleBar.setPosition(MapGravity.LEFT|MapGravity.BOTTOM, 20, 20);
                compass = kakaoMap.getCompass();
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_scale_bar_visible:
                if (isChecked) {
                    scaleBar.show();
                } else {
                    scaleBar.hide();
                }
                break;
            case R.id.ck_auto_hide:
                scaleBar.setAutoHide(isChecked);
                break;
            case R.id.ck_compass_visible:
                if (isChecked) {
                    compass.show();
                } else {
                    compass.hide();
                }
                break;
            case R.id.ck_back_to_north:
                compass.setBackToNorthOnClick(isChecked);
                break;
        }
    }
}
