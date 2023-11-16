package com.kakao.maps.open.android.kakaomap;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.Poi;
import com.kakao.vectormap.PoiScale;
import com.kakao.vectormap.mapwidget.InfoWindow;
import com.kakao.vectormap.mapwidget.InfoWindowOptions;
import com.kakao.vectormap.mapwidget.component.GuiImage;
import com.kakao.vectormap.mapwidget.component.GuiLayout;
import com.kakao.vectormap.mapwidget.component.GuiText;
import com.kakao.vectormap.mapwidget.component.Orientation;

import java.util.Arrays;


public class PoiDemoActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private InfoWindow infoWindow;
    private TextView tvSupportedLanguage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_overview);

        setTitle("Poi Demo");

        tvSupportedLanguage = findViewById(R.id.tv_supported_language);

        final MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;
                tvSupportedLanguage.setText("Supported Languages: "
                        + Arrays.toString(kakaoMap.getSupportedLanguages()));

                kakaoMap.setOnMapClickListener(new KakaoMap.OnMapClickListener() {
                    @Override
                    public void onMapClicked(KakaoMap kakaoMap, LatLng position, PointF screenPoint, Poi poi) {
                        showInfoWindow(position, poi);
                    }
                });
            }
        });
    }

    private void showInfoWindow(LatLng position, Poi poi) {
        if (infoWindow != null) {
            infoWindow.remove();
        }

        GuiLayout body = new GuiLayout(Orientation.Vertical);
        body.setPadding(15, 15, 15, 13);
        GuiImage image = new GuiImage(R.drawable.window_body, true);
        image.setFixedArea(7, 7, 7, 7);
        body.setBackground(image);

        GuiText text = new GuiText("isPoi= " + poi.isPoi());
        text.setTextSize(23);
        text.paddingRight = 13;

        body.addView(text);

        if (poi.isPoi()) {
            text = new GuiText("LayerId=" + poi.getLayerId());
            text.setTextSize(23);
            text.paddingTop = 8;
            text.setTextColor(Color.parseColor("#003F63"));
            body.addView(text);

            text = new GuiText("PoiId=" + poi.getPoiId());
            text.setTextSize(23);
            text.paddingTop = 8;
            text.setTextColor(Color.parseColor("#003F63"));
            body.addView(text);

            text = new GuiText("Name=" + poi.getName());
            text.setTextSize(23);
            text.paddingTop = 8;
            text.setTextColor(Color.parseColor("#003F63"));
            body.addView(text);
        }

        InfoWindowOptions options = InfoWindowOptions.from(position);
        options.setBody(body);
        options.setBodyOffset(0, -4);
        options.setTail(new GuiImage(R.drawable.window_tail, false));

        infoWindow = kakaoMap.getMapWidgetManager().getInfoWindowLayer().addInfoWindow(options);
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_poi_visible:
                kakaoMap.setPoiVisible(isChecked);
                break;
            case R.id.ck_poi_clickable:
                kakaoMap.setPoiClickable(isChecked);
                break;
        }
    }

    public void onScaleClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_poi_small:
                kakaoMap.setPoiScale(PoiScale.SMALL);
                break;
            case R.id.btn_poi_regular:
                kakaoMap.setPoiScale(PoiScale.REGULAR);
                break;
            case R.id.btn_poi_large:
                kakaoMap.setPoiScale(PoiScale.LARGE);
                break;
            case R.id.btn_poi_xlarge:
                kakaoMap.setPoiScale(PoiScale.XLARGE);
                break;
        }
    }
}
