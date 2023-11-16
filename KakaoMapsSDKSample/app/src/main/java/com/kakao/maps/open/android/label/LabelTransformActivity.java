package com.kakao.maps.open.android.label;

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
import com.kakao.vectormap.animation.Interpolation;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.PathOptions;
import com.kakao.vectormap.label.TrackingManager;
import com.kakao.vectormap.shape.DotPoints;
import com.kakao.vectormap.shape.Polygon;
import com.kakao.vectormap.shape.PolygonOptions;
import com.kakao.vectormap.shape.PolygonStyles;
import com.kakao.vectormap.shape.PolygonStylesSet;
import com.kakao.vectormap.shape.ShapeAnimator;
import com.kakao.vectormap.shape.animation.CircleWave;
import com.kakao.vectormap.shape.animation.CircleWaves;


public class LabelTransformActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private ShapeAnimator shapeAnimator;
    private LabelLayer labelLayer;
    private Label centerLabel, directionLabel, chatLabel;
    private Polygon animationPolygon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movable_label);
        setTitle("Label Transform Demo");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public LatLng getPosition() {
                return LatLng.from(37.394660,127.111182);
            }

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;
                labelLayer = kakaoMap.getLabelManager().getLayer();
                LatLng pos = kakaoMap.getCameraPosition().getPosition();

                // circleWave 애니메이션을 위한 Polygon 및 Animator 미리 생성
                animationPolygon = kakaoMap.getShapeManager().getLayer().addPolygon(
                        PolygonOptions.from("circlePolygon")
                                .setDotPoints(DotPoints.fromCircle(pos, 1.0f))
                                .setStylesSet(PolygonStylesSet.from(
                                        PolygonStyles.from(Color.parseColor("#f55d44")))));

                CircleWaves circleWaves = CircleWaves.from("circleWaveAnim",
                                CircleWave.from(1, 0, 0, 200))
                        .setHideShapeAtStop(false)
                        .setInterpolation(Interpolation.CubicInOut)
                        .setDuration(1500).setRepeatCount(100);
                shapeAnimator = kakaoMap.getShapeManager().addAnimator(circleWaves);

                createLabels(pos);
            }
        });
    }

    private void createLabels(LatLng pos) {
        // 중심 라벨 생성
        centerLabel = labelLayer.addLabel(LabelOptions.from("dotLabel", pos)
                .setStyles(LabelStyle.from(R.drawable.choonsik).setAnchorPoint(0.5f, 0.5f))
                .setRank(1));

        // 중심라벨에 방향라벨 연결 - 중심라벨의 회전값 및 Transform 을 공유하기 위해 addShareTransform 사용
        directionLabel = labelLayer.addLabel(LabelOptions.from("directionLabel", pos)
                .setStyles(LabelStyle.from(R.drawable.red_direction_area)
                        .setAnchorPoint(0.5f, 0.7f)).setRank(0));
        centerLabel.addShareTransform(directionLabel);

        // 중심라벨에 말풍선라벨 연결 - 중심라벨의 위치값만 공유하기 위해 addSharePosition 사용
        chatLabel = labelLayer.addLabel(LabelOptions.from("followingLabel", pos)
                        .setStyles(LabelStyle.from(R.drawable.blue_chat)
                                .setAnchorPoint(0.5f, 0.5f)).setVisible(false));
        chatLabel.changePixelOffset(60, -60);
        centerLabel.addSharePosition(chatLabel);

        // 중심라벨에 애니메이션 폴리곤 연결
        centerLabel.addShareTransform(animationPolygon);
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_attach_polygon:
                if (isChecked) {
                    shapeAnimator.addPolygons(animationPolygon);
                    shapeAnimator.setHideShapeAtStop(true);
                    shapeAnimator.start();
                } else {
                    shapeAnimator.stop();
                }
                break;
            case R.id.ck_add_shared_label:
                if (isChecked) {
                    chatLabel.show();
                } else {
                    chatLabel.hide();
                }
                break;
            case R.id.ck_tracking_mode:
                TrackingManager trackingManager = kakaoMap.getTrackingManager();
                ((CheckBox) findViewById(R.id.ck_tracking_rotation)).setEnabled(isChecked);
                if (isChecked) {
                    if (directionLabel != null) {
                        trackingManager.startTracking(directionLabel);
                    } else {
                        Toast.makeText(getBaseContext(),
                                "DirectionLabel is null.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    trackingManager.stopTracking();
                }
                break;
            case R.id.ck_tracking_rotation:
                trackingManager = kakaoMap.getTrackingManager();
                trackingManager.setTrackingRotation(isChecked);
                break;

        }
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_set_pos:
                LatLng currentPos = centerLabel.getPosition();
                centerLabel.moveTo(LatLng.from(currentPos.getLatitude() - 0.0003,
                        currentPos.getLongitude() - 0.0003));
                break;
            case R.id.btn_set_rotation:
                double currentAngle = centerLabel.getRotation();
                centerLabel.rotateTo((float) currentAngle - 0.5f);
                break;
            case R.id.btn_move_to:
                currentPos = centerLabel.getPosition();
                centerLabel.moveTo(LatLng.from(currentPos.getLatitude() + 0.0006,
                        currentPos.getLongitude() + 0.0006), 800);
                break;
            case R.id.btn_rotate_to:
                currentAngle = centerLabel.getRotation();
                centerLabel.rotateTo((float) currentAngle + 0.5f, 800);
                break;
            case R.id.btn_move_on_path:
                currentPos = centerLabel.getPosition();
                centerLabel.moveOnPath(PathOptions.fromPath(currentPos,
                        LatLng.from(currentPos.getLatitude(), currentPos.getLongitude() - 0.0003),
                        LatLng.from(currentPos.getLatitude(), currentPos.getLongitude() - 0.0006),
                        LatLng.from(currentPos.getLatitude(), currentPos.getLongitude() - 0.0009),
                        LatLng.from(currentPos.getLatitude() + 0.0003, currentPos.getLongitude() - 0.0009),
                        LatLng.from(currentPos.getLatitude() + 0.0006, currentPos.getLongitude() - 0.0009),
                        LatLng.from(currentPos.getLatitude() + 0.0009, currentPos.getLongitude() - 0.0009),
                        LatLng.from(currentPos.getLatitude() + 0.001, currentPos.getLongitude())).setDuration(5000));
                break;
            case R.id.btn_move_on_path_direction:
                currentPos = centerLabel.getPosition();
                centerLabel.moveOnPath(PathOptions.fromPath(currentPos,
                        LatLng.from(currentPos.getLatitude(), currentPos.getLongitude() - 0.0003),
                        LatLng.from(currentPos.getLatitude(), currentPos.getLongitude() - 0.0006),
                        LatLng.from(currentPos.getLatitude(), currentPos.getLongitude() - 0.0009),
                        LatLng.from(currentPos.getLatitude() + 0.003, currentPos.getLongitude())).setDuration(5000), true);
                break;
        }
    }
}
