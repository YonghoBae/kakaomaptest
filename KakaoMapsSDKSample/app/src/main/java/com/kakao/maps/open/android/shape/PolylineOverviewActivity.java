package com.kakao.maps.open.android.shape;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.maps.open.android.route.GeoJsonParser;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.shape.DotPoints;
import com.kakao.vectormap.shape.MapPoints;
import com.kakao.vectormap.shape.Polyline;
import com.kakao.vectormap.shape.PolylineOptions;
import com.kakao.vectormap.shape.PolylineStyle;
import com.kakao.vectormap.shape.PolylineStyles;
import com.kakao.vectormap.shape.PolylineStylesSet;
import com.kakao.vectormap.shape.ShapeManager;

import java.util.List;


public class PolylineOverviewActivity extends AppCompatActivity {

    private int duration = 500;
    private KakaoMap kakaoMap;
    private ShapeManager shapeManager;
    private GeoJsonParser.Result[] areaResult;
    private List<MapPoints> mapPoints;
    private Polyline circle, rectangle, multiPolyline, byLevelPolyline, areaPolyline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polyline_overview);
        setTitle("Polyline Overview");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;
                shapeManager = kakaoMap.getShapeManager();

                kakaoMap.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
                    @Override
                    public void onCameraMoveEnd(KakaoMap kakaoMap,
                                                CameraPosition cameraPosition,
                                                GestureType gestureType) {
                        ((TextView) findViewById(R.id.tv_camera_state))
                                .setText("Current ZoomLevel: " + cameraPosition.getZoomLevel());
                    }
                });

                new GeoJsonParser(getBaseContext(), new GeoJsonParser.OnCompleteListener() {
                    @Override
                    public void onComplete(GeoJsonParser.Result[] results) {
                        areaResult = results;
                    }
                }).execute(new String[] {"geo_gyeonggi_do.json"});
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_circle:
                if (isChecked) {
                    LatLng center = LatLng.from(37.394660,127.111182);
                    circle = shapeManager.getLayer().addPolyline(getCircleOptions(center));
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(center, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeManager.getLayer().remove(circle);
                }
                break;
            case R.id.ck_rectangle:
                if (isChecked) {
                    LatLng center = LatLng.from(37.397437,127.118135);
                    rectangle = shapeManager.getLayer().addPolyline(getRectangleOptions(center));
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(center, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeManager.getLayer().remove(rectangle);
                }
                break;
            case R.id.ck_multi_polyline:
                if (isChecked) {
                    LatLng pos = LatLng.from(37.392160,127.119754);
                    multiPolyline = shapeManager.getLayer().addPolyline(getMultiPolylineOptions(pos));
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeManager.getLayer().remove(multiPolyline);
                }
                break;
            case R.id.ck_level_polyline:
                LatLng center = LatLng.from(37.401750, 127.109656);
                if (isChecked) {
                    byLevelPolyline = shapeManager.getLayer().addPolyline(getByLevelOptions(center));
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(center, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeManager.getLayer().remove(byLevelPolyline);
                }
                break;
            case R.id.ck_area_polyline:
                if (isChecked) {
                    areaPolyline = shapeManager.getLayer().addPolyline(getAreaOptions());
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(
                                    LatLng.from(37.394660,127.111182), 8),
                            CameraAnimation.from(duration));
                } else {
                    shapeManager.getLayer().remove(areaPolyline);
                }
                break;
        }
    }

    PolylineOptions getCircleOptions(LatLng center) {
        return PolylineOptions.from().setDotPoints(DotPoints.fromCircle(center, 200))
                .setStylesSet(PolylineStylesSet.from(PolylineStyles.from(10,
                        Color.parseColor("#078c03"))));
    }

    PolylineOptions getRectangleOptions(LatLng center) {
        return PolylineOptions.from(DotPoints.fromRectangle(center, 300, 300),
                10, Color.parseColor("#104D73"));
    }

    PolylineOptions getMultiPolylineOptions(LatLng center) {
        return PolylineOptions.from(DotPoints.fromCircle(center, 200),
                10, Color.parseColor("#078c03"))
                .addPolyline(DotPoints.fromCircle(center, 300),
                        PolylineStyle.from(10, Color.RED));
    }

    PolylineOptions getByLevelOptions(LatLng center) {
        return PolylineOptions.from(DotPoints.fromPoints(center, new PointF(-75, 150), new PointF(75, 150),
                new PointF(150, 0), new PointF(75, -150), new PointF(-75, -150),
                new PointF(-150, 0), new PointF(-75, 150)),
                PolylineStyle.from(20, Color.parseColor("#511f73")).setZoomLevel(12),
                        PolylineStyle.from(20, Color.parseColor("#26a699")).setZoomLevel(14),
                        PolylineStyle.from(20, Color.parseColor("#f28705"),
                                3, Color.parseColor("#f20530")).setZoomLevel(16));
    }

    PolylineOptions getAreaOptions() {
        return PolylineOptions.from(MapPoints.fromLatLng(areaResult[0].coordinates),
                PolylineStyle.from(10, Color.parseColor("#80ff2c35"), 3, Color.RED));
    }
}
