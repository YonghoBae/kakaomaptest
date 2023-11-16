package com.kakao.maps.open.android.camera;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelOptions;

public class CameraUpdateDemoActivity extends AppCompatActivity implements
        KakaoMap.OnCameraMoveEndListener, KakaoMap.OnCameraMoveStartListener {

    private double tiltAngle = 0;
    private double rotationAngle = 0;
    private KakaoMap kakaoMap;
    private Label centerPointLabel;
    private CheckBox ckAnimate, ckAutoElevation, ckConsecutive, ckDuration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_update_demo);
        setTitle("Camera Demo");

        ckAnimate = findViewById(R.id.ck_animate);
        ckAutoElevation = findViewById(R.id.ck_auto_elevation);
        ckConsecutive = findViewById(R.id.ck_consecutive);
        ckDuration = findViewById(R.id.ck_duration);

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;
                kakaoMap.setOnCameraMoveStartListener(CameraUpdateDemoActivity.this);
                kakaoMap.setOnCameraMoveEndListener(CameraUpdateDemoActivity.this);

                centerPointLabel = kakaoMap.getLabelManager().getLayer()
                        .addLabel(LabelOptions.from(kakaoMap.getCameraPosition().getPosition())
                                .setStyles(R.drawable.red_dot_marker));
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_animate:
                findViewById(R.id.ck_auto_elevation).setEnabled(isChecked);
                findViewById(R.id.ck_consecutive).setEnabled(isChecked);
                findViewById(R.id.ck_duration).setEnabled(isChecked);
                break;
        }
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_gangnam:
                moveCamera(LatLng.from(37.497838,127.027576));
                break;
            case R.id.btn_pangyo:
                moveCamera(LatLng.from(37.394660,127.111182));
                break;
            case R.id.btn_rotate:
                rotate();
                break;
            case R.id.btn_tilt:
                tilt();
                break;
            case R.id.btn_zoom_in:
                kakaoMap.moveCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.btn_zoom_out:
                kakaoMap.moveCamera(CameraUpdateFactory.zoomOut());
                break;
            case R.id.btn_clear:
                CameraPosition clearPosition = CameraPosition.from(
                        new CameraPosition.Builder(kakaoMap.getCameraPosition())
                                .setTiltAngle(0.0f).setRotationAngle(0.0f));
                kakaoMap.moveCamera(CameraUpdateFactory.newCameraPosition(clearPosition));
                break;
        }
    }

    @Override
    public void onCameraMoveStart(KakaoMap kakaoMap, GestureType gestureType) {
        Toast.makeText(this, "onCameraMoveStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveEnd(KakaoMap kakaoMap, CameraPosition cameraPosition,
                                GestureType gestureType) {

        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        builder.append("MinZoomLevel : ").append(kakaoMap.getMinMapLevel()).append("\n")
                .append("MaxZoomLevel : ").append(kakaoMap.getMaxMapLevel()).append("\n")
                .append("MinCameraLevel : ").append(kakaoMap.getMinZoomLevel()).append("\n")
                .append("CurrentLevel : ").append(cameraPosition.getZoomLevel()).append("\n")
                .append("CurrentHeight : ").append(cameraPosition.getHeight()).append("\n")
                .append("Tilt : ").append((int) Math.toDegrees(cameraPosition.getTiltAngle()))
                .append("°").append("\n")
                .append("Rotate : ").append((int) Math.toDegrees(cameraPosition.getRotationAngle()))
                .append("°").append("\n")
                .append("Lat: ").append(cameraPosition.getPosition().getLatitude())
                .append("\n")
                .append("Lng: ").append(cameraPosition.getPosition().getLongitude());
        ((TextView) findViewById(R.id.tv_status)).setText(builder.toString());

        if (centerPointLabel != null) {
            centerPointLabel.moveTo(cameraPosition.getPosition());
        }
    }

    private void moveCamera(LatLng position) {
        if (ckAnimate.isChecked()) {
            kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(position),
                    CameraAnimation.from(ckDuration.isChecked() ? 1500 : 0, ckAutoElevation.isChecked(),
                            ckConsecutive.isChecked()));
        } else {
            kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(position));
        }
    }

    private void rotate() {
        rotationAngle = (rotationAngle + 30) % 360;

        if (ckAnimate.isChecked()) {
            kakaoMap.moveCamera(CameraUpdateFactory.rotateTo(Math.toRadians(rotationAngle)),
                    CameraAnimation.from(ckDuration.isChecked() ? 1500 : 0, ckAutoElevation.isChecked(),
                            ckConsecutive.isChecked()));
        } else {
            kakaoMap.moveCamera(CameraUpdateFactory.rotateTo(Math.toRadians(rotationAngle)));
        }
    }

    private void tilt() {
        tiltAngle = (tiltAngle + 10) % 60;

        if (ckAnimate.isChecked()) {
            kakaoMap.moveCamera(CameraUpdateFactory.tiltTo(Math.toRadians(tiltAngle)),
                    CameraAnimation.from(ckDuration.isChecked() ? 1500 : 0, ckAutoElevation.isChecked(),
                            ckConsecutive.isChecked()));
        } else {
            kakaoMap.moveCamera(CameraUpdateFactory.tiltTo(Math.toRadians(tiltAngle)));
        }
    }
}
