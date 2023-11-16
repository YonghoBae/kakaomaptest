package com.kakao.maps.open.android.dimscreen;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kakao.maps.open.android.R;
import com.kakao.maps.open.android.shape.PolygonBaseActivity;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.shape.DimScreenCover;
import com.kakao.vectormap.shape.DimScreenLayer;
import com.kakao.vectormap.shape.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DimScreenOverviewActivity extends PolygonBaseActivity {

    private KakaoMap kakaoMap;
    private DimScreenLayer dimScreenLayer;
    private List<Polygon> polygonList = new ArrayList<Polygon>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimscreen_layer);
        setTitle("DimScreen Demo");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull KakaoMap map) {
                kakaoMap = map;
                dimScreenLayer = kakaoMap.getDimScreenManager().getDimScreenLayer();
                kakaoMap.getLabelManager().getLayer().addLabel(
                        LabelOptions.from(kakaoMap.getCameraPosition().getPosition())
                                .setStyles(LabelStyle.from(R.drawable.pink_marker)));
                kakaoMap.getCompass().show();
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_dimscreen_visible:
                dimScreenLayer.setVisible(isChecked);
                findViewById(R.id.ck_set_color).setEnabled(isChecked);
                break;
            case R.id.ck_set_color:
                if (isChecked) {
                    dimScreenLayer.setColor(Color.parseColor("#609033FF"));
                } else {
                    dimScreenLayer.setColor(Color.parseColor("#60000000"));
                }
                break;
        }
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cover_map:
                dimScreenLayer.setDimScreenCover(DimScreenCover.Map);
                break;
            case R.id.btn_cover_map_and_label:
                dimScreenLayer.setDimScreenCover(DimScreenCover.MapAndLabel);
                break;
            case R.id.btn_cover_all:
                dimScreenLayer.setDimScreenCover(DimScreenCover.All);
                break;
            case R.id.btn_add_polygon:
                LatLng pos = LatLng.from(37.392160,127.119754);
                polygonList.add(dimScreenLayer.addPolygon(getMultiPolygonOptions(pos)));
                kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 15));
                break;
            case R.id.btn_add_mappoint_polygons:
                dimScreenLayer.addMapPointPolygons(getMapPolygons(), new DimScreenLayer.OnPolygonCreateCallback() {
                    @Override
                    public void onPolygonCreated(DimScreenLayer layer, Polygon... polygons) {
                        polygonList.addAll(Arrays.asList(polygons));
                        Toast.makeText(getBaseContext(), "Polygon Created!", Toast.LENGTH_SHORT).show();
                    }
                });
                kakaoMap.moveCamera(CameraUpdateFactory.zoomTo(13));
                break;
            case R.id.btn_remove:
                if (!polygonList.isEmpty()) {
                    Polygon removable = polygonList.remove(polygonList.size() - 1);
                    dimScreenLayer.remove(removable);
                }
                break;
            case R.id.btn_removeAll:
                dimScreenLayer.clearAll();
                break;
        }
    }
}
