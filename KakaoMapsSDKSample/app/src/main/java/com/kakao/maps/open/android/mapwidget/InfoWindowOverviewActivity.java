package com.kakao.maps.open.android.mapwidget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.mapwidget.InfoWindow;
import com.kakao.vectormap.mapwidget.InfoWindowLayer;
import com.kakao.vectormap.mapwidget.InfoWindowOptions;
import com.kakao.vectormap.mapwidget.component.GuiImage;
import com.kakao.vectormap.mapwidget.component.GuiLayout;
import com.kakao.vectormap.mapwidget.component.GuiText;
import com.kakao.vectormap.mapwidget.component.Orientation;


public class InfoWindowOverviewActivity extends AppCompatActivity {

    private InfoWindowLayer infoWindowLayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_window);

        setTitle("InfoWindow Demo");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap kakaoMap) {
                infoWindowLayer = kakaoMap.getMapWidgetManager().getInfoWindowLayer();
                infoWindowLayer.addInfoWindow(getSimpleLayout("simpleLayout"));
                infoWindowLayer.addInfoWindow(getComplexLayout("complexLayout"));

                kakaoMap.setOnInfoWindowClickListener(new KakaoMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClicked(KakaoMap kakaoMap, InfoWindow infoWindow, String guiId) {
                        Toast.makeText(InfoWindowOverviewActivity.this,
                                infoWindow.getId() + " clicked!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.btn_simple_layout:
                if (isChecked) {
                    infoWindowLayer.getInfoWindow("simpleLayout").show();
                } else {
                    infoWindowLayer.getInfoWindow("simpleLayout").hide();
                }
                break;
            case R.id.btn_complex_layout:
                if (isChecked) {
                    infoWindowLayer.getInfoWindow("complexLayout").show();
                } else {
                    infoWindowLayer.getInfoWindow("complexLayout").hide();
                }
                break;
        }
    }

    private InfoWindowOptions getSimpleLayout(String infoWindowId) {
        GuiLayout body = new GuiLayout(Orientation.Horizontal);
        body.setPadding(20, 20, 20, 18);

        GuiImage image = new GuiImage(R.drawable.window_body, true);
        image.setFixedArea(7, 7, 7, 7);
        body.setBackground(image);

        GuiText text = new GuiText("Simple Layout!");
        text.setTextSize(30);
        body.addView(text);

        InfoWindowOptions options = InfoWindowOptions.from(infoWindowId,
                LatLng.from(37.402005, 127.108621));
        options.setBody(body);
        options.setBodyOffset(0, -4);
        options.setTail(new GuiImage(R.drawable.window_tail, false));
        options.setVisible(false);
        return options;
    }

    private InfoWindowOptions getComplexLayout(String infoWindowId) {
        // body
        GuiLayout body = new GuiLayout(Orientation.Vertical);
        body.setPadding(15, 15, 15, 13);
        GuiImage image = new GuiImage(R.drawable.window_body, true);
        image.setFixedArea(7, 7, 7, 7);
        body.setBackground(image);

        // upper layout
        GuiText text = new GuiText("Complex Layout!");
        text.setTextSize(23);
        text.paddingRight = 13;

        GuiLayout upperLayout = new GuiLayout(Orientation.Horizontal);
        upperLayout.addView(text);
        upperLayout.addView(new GuiImage(R.drawable.red_dot_marker, false));

        // lower layout
        GuiText text2 = new GuiText("한글 ABC 123 !@#$%^&*() ,.:;");
        text2.setTextSize(23);
        text2.paddingTop = 8;
        text2.setTextColor(Color.parseColor("#003F63"));

        body.addView(upperLayout);
        body.addView(text2);

        InfoWindowOptions options = InfoWindowOptions.from(infoWindowId,
                LatLng.from(37.397437,127.118135));
        options.setBody(body);
        options.setBodyOffset(0, -4);
        options.setTail(new GuiImage(R.drawable.window_tail, false));
        options.setVisible(false);
        return options;
    }
}
