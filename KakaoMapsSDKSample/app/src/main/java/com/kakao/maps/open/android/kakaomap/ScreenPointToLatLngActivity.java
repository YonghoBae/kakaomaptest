package com.kakao.maps.open.android.kakaomap;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelOptions;

public class ScreenPointToLatLngActivity extends AppCompatActivity {
    private Label label;
    private TextView tvMapViewLatLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_to_latlng);

        setTitle("ScreenPoint to LatLng Demo");

        tvMapViewLatLng = findViewById(R.id.tv_map_view_latlng);

        final MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap map) {
                Rect viewport = map.getViewport();
                int x = viewport.width() / 2;
                int y = viewport.height() / 2;

                LatLng centerPosition = map.fromScreenPoint(x, y);
                tvMapViewLatLng.setText("Lat = "
                        + centerPosition.latitude + "\nLng = " + centerPosition.longitude);

                // toScreenPoint() 를 이용하여 지리적 좌표를 스크린 좌표로 변환할 수 있습니다.
                Point point = map.toScreenPoint(centerPosition);

                label = map.getLabelManager().getLayer().addLabel(
                        LabelOptions.from(map.fromScreenPoint(point.x, point.y))
                                .setStyles(R.drawable.green_marker));

                map.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
                    @Override
                    public void onCameraMoveEnd(KakaoMap kakaoMap, CameraPosition cameraPosition,
                                                GestureType gestureType) {
                        Rect viewport = map.getViewport();

                        int x = viewport.width() / 2;
                        int y = viewport.height() / 2;

                        // fromScreenPoint() 를 이용하여 스크린 좌표를 지리적 좌표로 변환할 수 있습니다.
                        LatLng position = map.fromScreenPoint(x, y);
                        label.moveTo(position);

                        tvMapViewLatLng.setText("Lat = " + position.latitude
                                + "\nLng = " + position.longitude);
                    }
                });
            }
        });
    }
}
