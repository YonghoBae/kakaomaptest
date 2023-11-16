package com.kakao.maps.open.android.etc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.MapCapture;
import com.kakao.maps.open.android.R;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.graphics.gl.GLSurfaceView;

/**
 * 지도 캡쳐 예제는 다양한 사용예시 중 하나이며, 지도 API 사용과는 무관합니다.
 */
public class MapCaptureDemoActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_capture);
        setTitle("Map Capture Demo");

        mapView = findViewById(R.id.map_view);
        mapView.start(new MapLifeCycleCallback() {
            @Override
            public void onMapDestroy() {

            }

            @Override
            public void onMapError(Exception error) {

            }
        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull KakaoMap kakaoMap) {

            }
        });
    }

    public void onButtonClicked(View view) {
        if (mapView == null) {
            Toast.makeText(getApplicationContext(), "지도가 준비되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        MapCapture.capture(this, (GLSurfaceView) mapView.getSurfaceView(), new MapCapture.OnCaptureListener() {
            @Override
            public void onCaptured(boolean isSucceed, String fileName) {
                if (isSucceed) {
                    ((TextView) findViewById(R.id.tv_capture_file_name)).setText("FileName: " + fileName);
                    Toast.makeText(getApplicationContext(), "캡쳐가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    ((TextView) findViewById(R.id.tv_capture_file_name)).setText("FileName: ");
                    Toast.makeText(getApplicationContext(), "캡쳐에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
