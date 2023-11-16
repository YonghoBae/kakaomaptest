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
import com.kakao.vectormap.animation.Interpolation;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.shape.DimScreenLayer;
import com.kakao.vectormap.shape.DotPoints;
import com.kakao.vectormap.shape.MapPoints;
import com.kakao.vectormap.shape.Polygon;
import com.kakao.vectormap.shape.PolygonOptions;
import com.kakao.vectormap.shape.PolygonStyle;
import com.kakao.vectormap.shape.ShapeLayer;
import com.kakao.vectormap.shape.ShapeManager;
import com.kakao.vectormap.shape.animation.CircleWave;
import com.kakao.vectormap.shape.animation.CircleWaves;


public class PolygonOverviewActivity extends AppCompatActivity {

    private int duration = 500;
    private KakaoMap kakaoMap;
    private ShapeLayer shapeLayer;
    private ShapeManager shapeManager;
    private DimScreenLayer dimScreenLayer;
    private GeoJsonParser.Result[] areaResult;
    private Polygon circle, rectangle, holePolygon, multiPolygon, 
            byLevelPolygon, areaPolygon, dimAreaPolygon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon_overview);
        setTitle("Polygon Overview");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;
                kakaoMap.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
                    @Override
                    public void onCameraMoveEnd(KakaoMap kakaoMap,
                                                CameraPosition cameraPosition,
                                                GestureType gestureType) {

                        ((TextView) findViewById(R.id.tv_camera_state))
                                .setText("Current ZoomLevel: " + cameraPosition.getZoomLevel());
                    }
                });

                shapeManager = map.getShapeManager();
                shapeLayer = shapeManager.getLayer();
                dimScreenLayer = map.getDimScreenManager().getDimScreenLayer();

                createAnimator();

                new GeoJsonParser(getBaseContext(), new GeoJsonParser.OnCompleteListener() {
                    @Override
                    public void onComplete(GeoJsonParser.Result[] results) {
                        areaResult = results;
                    }
                }).execute(new String[] {"geo_bundang_0.json", "geo_bundang_1.json", "geo_bundang_2.json",
                        "geo_bundang_3.json"});

            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_circle:
                if (isChecked) {
                    LatLng center = LatLng.from(37.394660,127.111182);
                    circle = shapeLayer.addPolygon(getCircleOptions(center, 200));
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(center, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeLayer.remove(circle);
                }
                break;
            case R.id.ck_rectangle:
                if (isChecked) {
                    LatLng center = LatLng.from(37.397437,127.118135);
                    rectangle = shapeLayer.addPolygon(getRectangleOptions(center, 300, 300));
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(center, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeLayer.remove(rectangle);
                }
                break;
            case R.id.ck_with_hole:
                if (isChecked) {
                    LatLng center = LatLng.from(37.397298,127.113719);
                    holePolygon = shapeLayer.addPolygon(getHoleCircleOptions(center, 250));
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(center, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeLayer.remove(holePolygon);
                }
                break;
            case R.id.ck_multi_polygon:
                if (isChecked) {
                    LatLng center = LatLng.from(37.392160,127.119754);
                    multiPolygon = shapeLayer.addPolygon(getMultiPolygonOptions(center));
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(center, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeLayer.remove(multiPolygon);
                }
                break;
            case R.id.ck_level_polygon:
                LatLng center = LatLng.from(37.401750, 127.109656);
                if (isChecked) {
                    byLevelPolygon = shapeLayer.addPolygon(getPolygonByLevel(center));;
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(center, 15),
                            CameraAnimation.from(duration));
                } else {
                    shapeLayer.remove(byLevelPolygon);
                }
                break;
            case R.id.ck_area_polygon:
                if (areaResult == null) {
                    return;
                }

                if (isChecked) {
                    areaPolygon = shapeLayer.addPolygon(getMultiAreaPolygon());
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(
                            LatLng.from(37.394660,127.111182), 13),
                            CameraAnimation.from(duration));
                } else {
                    shapeLayer.remove(areaPolygon);
                }
                break;
            case R.id.ck_circle_wave:
                if (isChecked) {
                    shapeManager.getAnimator("animator1").start();
                    shapeManager.getAnimator("animator2").start();

                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(
                            LatLng.from(37.391026, 127.117083), 14),
                            CameraAnimation.from(duration));
                } else {
                    shapeManager.getAnimator("animator1").stop();
                    shapeManager.getAnimator("animator2").stop();
                }
                break;
            case R.id.ck_dim_layer:
                dimScreenLayer.setVisible(isChecked);
                if (isChecked) {
                    dimAreaPolygon = dimScreenLayer.addPolygon(getMultiAreaPolygon());
                    kakaoMap.moveCamera(CameraUpdateFactory
                                    .newCenterPosition(LatLng.from(37.410258, 127.121241), 13),
                            CameraAnimation.from(duration));
                } else {
                    dimScreenLayer.remove(dimAreaPolygon);
                }
                break;
        }
    }

    private void createAnimator() {
        // animator1 생성
        CircleWaves circleWaves = CircleWaves.from("animator1")
                .setDuration(3000)
                .setRepeatCount(10)
                .setInterpolation(Interpolation.CubicIn)
                .addCircleWave(CircleWave.from(0.7f, 0.0f, 0.0f, 100.0f));
        shapeManager.addAnimator(circleWaves);

        Polygon circle = shapeLayer.addPolygon(
                getCircleOptions(LatLng.from(37.3849851, 127.1174393), 1));
        Polygon rectangle = shapeLayer.addPolygon(
                getRectangleOptions(LatLng.from(37.391699, 127.119156), 1, 1));
        shapeManager.getAnimator("animator1").addPolygons(circle);
        shapeManager.getAnimator("animator1").addPolygons(rectangle);

        // animator2 생성
        circleWaves = CircleWaves.from("animator2")
                .setDuration(3000)
                .setRepeatCount(10)
                .setInterpolation(Interpolation.CubicInOut)
                .addCircleWave(CircleWave.from(0.7f, 0.0f, 0.0f, 100.0f));
        shapeManager.addAnimator(circleWaves);

        Polygon holeCircle = shapeLayer.addPolygon(
                getHoleCircleOptions(LatLng.from(37.392797, 127.112052), 1));
        shapeManager.getAnimator("animator2").addPolygons(holeCircle);

    }

    PolygonOptions getCircleOptions(LatLng center, int radius) {
        return PolygonOptions.from(DotPoints.fromCircle(center, radius),
                Color.parseColor("#078c03"));
    }

    PolygonOptions getRectangleOptions(LatLng center, int width, int height) {
        return PolygonOptions.from(DotPoints.fromRectangle(center, width, height),
                Color.parseColor("#104D73"));
    }

    PolygonOptions getHoleCircleOptions(LatLng center, int radius) {
        return PolygonOptions.from(DotPoints.fromCircle(center, radius)
                .setHoleCircle(radius * 0.7f), Color.parseColor("#abd904"));
    }

    PolygonOptions getMultiPolygonOptions(LatLng center) {
        return PolygonOptions.from(DotPoints.fromPoints(center, new PointF(-450, 150),
                new PointF(-150, 450), new PointF(-150, 150),
                new PointF(-450, 150)), Color.parseColor("#f55d44"))
                .addPolygon(DotPoints.fromPoints(center, new PointF(150, 150), new PointF(250, 350),
                        new PointF(550, 350), new PointF(450, 150),
                        new PointF(150, 150)), Color.parseColor("#ffc848"))
                .addPolygon(DotPoints.fromCircle(center, 200.0f).setHoleCircle(150.0f),
                        Color.parseColor("#2384D9"))
                .addPolygon(DotPoints.fromCircle(center, 120.0f).setHoleCircle(70.0f),
                        Color.parseColor("#50c3f2"));
    }

    PolygonOptions getPolygonByLevel(LatLng center) {
        return PolygonOptions.from(DotPoints.fromPoints(center, new PointF(-75, 150), new PointF(75, 150),
                new PointF(150, 0), new PointF(75, -150), new PointF(-75, -150),
                new PointF(-150, 0), new PointF(-75, 150)),
        PolygonStyle.from(Color.parseColor("#511f73")).setZoomLevel(12),
                        PolygonStyle.from(Color.parseColor("#f28705")).setZoomLevel(14),
                        PolygonStyle.from(Color.parseColor("#26a699"),
                                3, Color.parseColor("#f20530")).setZoomLevel(16));
    }

    PolygonOptions getMultiAreaPolygon() {
        return PolygonOptions.from(MapPoints.fromLatLng(areaResult[0].coordinates),
                PolygonStyle.from(Color.parseColor("#80ff2c35"), 3, Color.RED))
                .addPolygon(MapPoints.fromLatLng(areaResult[1].coordinates),
                        PolygonStyle.from(Color.parseColor("#8098a62e"), 3, Color.GREEN))
                .addPolygon(MapPoints.fromLatLng(areaResult[2].coordinates),
                        PolygonStyle.from(Color.parseColor("#803cbceb"), 3, Color.BLUE))
                .addPolygon(MapPoints.fromLatLng(areaResult[3].coordinates),
                        PolygonStyle.from(Color.parseColor("#80f2cb05"), 3, Color.YELLOW));
    }
}
