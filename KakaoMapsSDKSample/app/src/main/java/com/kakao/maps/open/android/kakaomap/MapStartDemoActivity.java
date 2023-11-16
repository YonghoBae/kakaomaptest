package com.kakao.maps.open.android.kakaomap;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;

public class MapStartDemoActivity extends AppCompatActivity {

    private MapView mapView;
    private TextView tvLat, tvLng, tvZoomLevel;

    private int startZoomLevel = 15;
    private LatLng startPosition = LatLng.from(37.394660,127.111182);   // 판교역

    // MapReadyCallback 을 통해 지도가 정상적으로 시작된 후에 수신할 수 있다.
    private KakaoMapReadyCallback readyCallback = new KakaoMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull KakaoMap kakaoMap) {
            Toast.makeText(getApplicationContext(), "Map Start!", Toast.LENGTH_SHORT).show();

            tvLat.setText(String.valueOf(startPosition.getLatitude()));
            tvLng.setText(String.valueOf(startPosition.getLongitude()));
            tvZoomLevel.setText(String.valueOf(startZoomLevel));

            Log.i("k3f", "startPosition: "
                    + kakaoMap.getCameraPosition().getPosition().toString());
            Log.i("k3f", "startZoomLevel: "
                    + kakaoMap.getZoomLevel());
        }

        @NonNull
        @Override
        public LatLng getPosition() {
            return startPosition;
        }

        @NonNull
        @Override
        public int getZoomLevel() {
            return startZoomLevel;
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
        setContentView(R.layout.activity_map_auth_start);
        setTitle("Map Start");

        tvLat = findViewById(R.id.tvLat);
        tvLng = findViewById(R.id.tvLng);
        tvZoomLevel = findViewById(R.id.tvZoomLevel);

        mapView = findViewById(R.id.map_view);
        mapView.start(lifeCycleCallback, readyCallback);
    }
}
