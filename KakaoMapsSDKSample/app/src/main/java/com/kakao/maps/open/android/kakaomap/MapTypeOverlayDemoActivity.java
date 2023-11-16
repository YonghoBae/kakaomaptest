package com.kakao.maps.open.android.kakaomap;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapOverlay;
import com.kakao.vectormap.MapType;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.MapViewInfo;

public class MapTypeOverlayDemoActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private MapType mapType;
    private MapOverlay mapOverlay;

    // MapReadyCallback 을 통해 지도가 정상적으로 시작된 후에 수신할 수 있다.
    private KakaoMapReadyCallback readyCallback = new KakaoMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull KakaoMap map) {
            Toast.makeText(getApplicationContext(), "Map Start!", Toast.LENGTH_SHORT).show();

            kakaoMap = map;
            kakaoMap.setOnMapViewInfoChangeListener(new KakaoMap.OnMapViewInfoChangeListener() {
                @Override
                public void onMapViewInfoChanged(MapViewInfo mapViewInfo) {
                    Toast.makeText(getApplicationContext(), "onMapViewInfoChanged("
                                    + mapViewInfo.getMapType() + ")", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMapViewInfoChangeFailed() {
                    Toast.makeText(getApplicationContext(), "onMapViewInfoChangeFailed",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    // MapLifeCycleCallback 을 통해 지도의 LifeCycle 관련 이벤트를 수신할 수 있다.
    private MapLifeCycleCallback lifeCycleCallback = new MapLifeCycleCallback() {

        @Override
        public void onMapResumed() {
            super.onMapResumed();
        }

        @Override
        public void onMapPaused() {
            super.onMapPaused();
        }

        @Override
        public void onMapDestroy() {
            Toast.makeText(getApplicationContext(), "onMapDestroy",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMapError(Exception error) {
            Toast.makeText(getApplicationContext(), error.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_type_overlay);
        setTitle("MapType & MapOverlay Demo");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(lifeCycleCallback, readyCallback);
    }

    public void onMapTypeClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_normal_map:
                kakaoMap.changeMapType(MapType.NORMAL);
                break;
            case R.id.btn_skyview:
                kakaoMap.changeMapType(MapType.SKYVIEW);
                break;
        }
    }

    public void onMapOverlayClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.ck_roadview_line:
                if (checked) {
                    kakaoMap.showOverlay(MapOverlay.ROADVIEW_LINE);
                } else {
                    kakaoMap.hideOverlay(MapOverlay.ROADVIEW_LINE);
                }
                break;
            case R.id.ck_bicycle_road:
                if (checked) {
                    kakaoMap.showOverlay(MapOverlay.BICYCLE_ROAD);
                } else {
                    kakaoMap.hideOverlay(MapOverlay.BICYCLE_ROAD);
                }
                break;
            case R.id.ck_hill_shading:
                if (checked) {
                    kakaoMap.showOverlay(MapOverlay.HILLSHADING);
                } else {
                    kakaoMap.hideOverlay(MapOverlay.HILLSHADING);
                }
                break;
            case R.id.ck_hybrid:
                if (checked) {
                    kakaoMap.showOverlay(MapOverlay.SKYVIEW_HYBRID);
                } else {
                    kakaoMap.hideOverlay(MapOverlay.SKYVIEW_HYBRID);
                }
                break;
        }
    }
}
