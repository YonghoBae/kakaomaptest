package com.kakao.maps.open.android.route;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.maps.open.android.Route;
import com.kakao.vectormap.CurveType;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.route.RouteLine;
import com.kakao.vectormap.route.RouteLineLayer;
import com.kakao.vectormap.route.RouteLineOptions;
import com.kakao.vectormap.route.RouteLineSegment;
import com.kakao.vectormap.route.RouteLineStyle;
import com.kakao.vectormap.route.RouteLineStyles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteLineOverviewActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private RouteLineLayer layer;
    private RouteLine simpleLine, curveLine, multiStyleLine, patternLine, byLevelLine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_line);
        setTitle("RouteLine Overview");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;
                layer = kakaoMap.getRouteLineManager().getLayer();

                kakaoMap.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
                    @Override
                    public void onCameraMoveEnd(KakaoMap kakaoMap,
                                                CameraPosition cameraPosition,
                                                GestureType gestureType) {
                        ((TextView) findViewById(R.id.tv_camera_state))
                                .setText("Current ZoomLevel: " + cameraPosition.getZoomLevel());
                    }
                });
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_simple:
                if (isChecked) {
                    RouteLineStyle style = RouteLineStyle.from(getBaseContext(),
                            R.style.SimpleRouteLineStyle);
                    RouteLineOptions options = RouteLineOptions.from(
                            Arrays.asList(RouteLineSegment.from(Route.SimplifyAll, style)));

                    simpleLine = layer.addRouteLine(options);
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(
                            LatLng.from(37.338549743448546,127.09368565409382), 16));
                } else {
                    layer.remove(simpleLine);
                }
                break;
            case R.id.ck_curved_line:
                if (isChecked) {
                    RouteLineStyle style = RouteLineStyle.from(getBaseContext(), R.style.AirPlanePattern);
                    RouteLineOptions options = RouteLineOptions.from(RouteLineSegment.from(
                                    Arrays.asList(LatLng.from(37.566536, 126.977966),
                                            LatLng.from(35.179554, 129.075638)), style)
                            .setCurveType(CurveType.LeftCurve));

                    curveLine = layer.addRouteLine(options);
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(
                            LatLng.from(37.400943, 127.109645), 7));
                } else {
                    layer.remove(curveLine);
                }
                break;
            case R.id.ck_multi_style:
                if (isChecked) {
                    RouteLineOptions options = RouteLineOptions.from(Arrays.asList(
                            RouteLineSegment.from(Route.Multi1, RouteLineStyle.from(getBaseContext(),
                                    R.style.RedRouteLineStyle)),
                            RouteLineSegment.from(Route.Multi2, RouteLineStyle.from(getBaseContext(),
                                    R.style.YellowRouteLineStyle)),
                            RouteLineSegment.from(Route.Multi3, RouteLineStyle.from(getBaseContext(),
                                    R.style.GreenRouteArrowLineStyle)),
                            RouteLineSegment.from(Route.Multi4, RouteLineStyle.from(getBaseContext(),
                                    R.style.BlueRouteArrowLineStyle)),
                            RouteLineSegment.from(Route.Multi5, RouteLineStyle.from(getBaseContext(),
                                    R.style.RedRouteArrowLineStyle)),
                            RouteLineSegment.from(Route.Multi6, RouteLineStyle.from(getBaseContext(),
                                    R.style.YellowRouteLineStyle)),
                            RouteLineSegment.from(Route.Multi7, RouteLineStyle.from(getBaseContext(),
                                    R.style.BlueRouteLineStyle))));

                    multiStyleLine = layer.addRouteLine(options);
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(LatLng.from(37.394882, 127.110457)),
                            CameraAnimation.from(500));
                } else {
                    layer.remove(multiStyleLine);
                }
                break;
            case R.id.ck_pattern:
                if (isChecked) {
                    RouteLineOptions options = RouteLineOptions.from(Arrays.asList(
                            RouteLineSegment.from(Route.Pattern1, RouteLineStyle.from(getBaseContext(),
                                    R.style.BusPattern)),
                            RouteLineSegment.from(Route.Pattern2, RouteLineStyle.from(getBaseContext(),
                                    R.style.BlueRouteArrowLineStyle)),
                            RouteLineSegment.from(Route.Pattern3, RouteLineStyle.from(getBaseContext(),
                                    R.style.BusPattern))
                    ));
                    patternLine = layer.addRouteLine(options);
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(
                            LatLng.from(37.394882, 127.110457), 15),
                            CameraAnimation.from(500));
                } else {
                    layer.remove(patternLine);
                }
                break;
            case R.id.ck_by_level:
                if (isChecked) {
                    RouteLineStyles styles = RouteLineStyles.from(RouteLineStyle.from(getBaseContext(),
                                    R.style.BlueRouteArrowLineStyle).setZoomLevel(10),
                            RouteLineStyle.from(getBaseContext(), R.style.SimpleRouteLineStyle)
                                    .setZoomLevel(15));

                    List<LatLng> points = new ArrayList<>();
                    points.addAll(Route.ByLevel1);
                    points.addAll(Route.ByLevel2);
                    points.addAll(Route.ByLevel3);
                    points.addAll(Route.ByLevel4);
                    points.addAll(Route.ByLevel5);
                    points.addAll(Route.ByLevel6);
                    points.addAll(Route.ByLevel7);

                    RouteLineOptions options = RouteLineOptions.from(RouteLineSegment.from(points, styles));
                    byLevelLine = layer.addRouteLine(options);
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(
                            LatLng.from(37.398670, 127.116457), 10),
                            CameraAnimation.from(500));
                } else {
                    byLevelLine.remove();
                }
                break;
        }
    }

}
