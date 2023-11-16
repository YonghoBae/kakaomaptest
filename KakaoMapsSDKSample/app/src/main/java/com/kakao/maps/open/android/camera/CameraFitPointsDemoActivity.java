package com.kakao.maps.open.android.camera;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.Poi;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelOptions;

import java.util.ArrayList;
import java.util.List;

public class CameraFitPointsDemoActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private TextView tvZoomLevel;

    private int padding = 100;
    private int maxZoomLevel = 13;
    private List<Label> selectedList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_fit_points_demo);
        setTitle("FitMapPoints Demo");

        tvZoomLevel = findViewById(R.id.tv_camera_state);
        ((TextView) findViewById(R.id.tv_padding)).setText(String.valueOf(padding));
        ((TextView) findViewById(R.id.tv_max_zoom_level)).setText(String.valueOf(maxZoomLevel));

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;

                kakaoMap.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
                    @Override
                    public void onCameraMoveEnd(@NonNull KakaoMap kakaoMap,
                                                @NonNull CameraPosition cameraPosition,
                                                @NonNull GestureType gestureType) {
                        tvZoomLevel.setText("ZoomLevel: " + cameraPosition.getZoomLevel());
                    }
                });

                kakaoMap.setOnMapClickListener(new KakaoMap.OnMapClickListener() {
                    @Override
                    public void onMapClicked(KakaoMap kakaoMap, LatLng position,
                                             PointF screenPoint, Poi poi) {

                        Label label = kakaoMap.getLabelManager().getLayer()
                                .addLabel(LabelOptions.from(position)
                                        .setStyles(R.drawable.red_dot_marker));
                        selectedList.add(label);
                    }
                });
            }
        });
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_can_fit:
                if (selectedList.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "지도에 클릭해서 좌표를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean canShowPoints = kakaoMap.canShowMapPoints(maxZoomLevel, getSelectedPoints());
                Toast.makeText(getApplicationContext(),
                        "canShowMapPoints: " + canShowPoints, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_fit:
                if (selectedList.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "지도에 클릭해서 좌표를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                kakaoMap.moveCamera(CameraUpdateFactory.fitMapPoints(getSelectedPoints(), padding),
                        CameraAnimation.from(500, true, true));
                break;
            case R.id.btn_clear:
                selectedList.clear();
                kakaoMap.getLabelManager().clearAll();
                break;
        }
    }

    private LatLng[] getSelectedPoints() {
        int count = selectedList.size();
        LatLng[] points = new LatLng[count];
        for (int i = 0; i < selectedList.size(); i++) {
            points[i] = selectedList.get(i).getPosition();
        }
        return points;
    }
}
