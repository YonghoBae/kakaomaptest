package com.kakao.maps.open.android.kakaomap;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.MapGravity;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;

public class ViewportPaddingChangeDemoActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private TextView tvViewportSize, tvPaddingSize;

    // MapReadyCallback 을 통해 지도가 정상적으로 시작된 후에 수신할 수 있다.
    private KakaoMapReadyCallback readyCallback = new KakaoMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull KakaoMap map) {
            kakaoMap = map;
            kakaoMap.getCompass().show();
            kakaoMap.getScaleBar().
                    setPosition(MapGravity.LEFT|MapGravity.BOTTOM, 20, 20);
            kakaoMap.getScaleBar().show();

            Toast.makeText(getApplicationContext(), "Map Start!", Toast.LENGTH_SHORT).show();
            tvViewportSize.setText(kakaoMap.getViewport().toString());
            tvPaddingSize.setText(kakaoMap.getPadding().toString());

            kakaoMap.setOnViewportChangeListener(new KakaoMap.OnViewportChangeListener() {
                @Override
                public void onViewportChanged(KakaoMap kakaoMap, Rect rect) {
                    Toast.makeText(getApplicationContext(), "Viewport Changed!",
                            Toast.LENGTH_SHORT).show();
                    tvViewportSize.setText(rect.toString());
                }
            });

            kakaoMap.setOnPaddingChangeListener(new KakaoMap.OnPaddingChangeListener() {
                @Override
                public void onViewportPaddingChanged(KakaoMap kakaoMap) {
                    Toast.makeText(getApplicationContext(), "Padding Changed!",
                            Toast.LENGTH_SHORT).show();
                    tvPaddingSize.setText(kakaoMap.getPadding().toString());
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
        setContentView(R.layout.activity_viewport_resize);
        setTitle("Viewport & Padding Demo");

        tvViewportSize = findViewById(R.id.tv_viewport_size);
        tvPaddingSize = findViewById(R.id.tv_padding_size);

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(lifeCycleCallback, readyCallback);
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_set_viewport:
                kakaoMap.setViewport(new Rect(100, 100, 600, 700));
//              kakaoMap.setViewport(100, 100, 500, 600);
                break;
            case R.id.btn_set_padding:
                kakaoMap.setPadding(0, 100, 100, 100);
                break;
        }
    }
}
