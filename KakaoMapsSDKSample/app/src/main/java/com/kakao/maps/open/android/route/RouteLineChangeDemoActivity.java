package com.kakao.maps.open.android.route;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.maps.open.android.Route;
import com.kakao.maps.open.android.ThreadTask;
import com.kakao.vectormap.CoordConverter;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.route.RouteLine;
import com.kakao.vectormap.route.RouteLineLayer;
import com.kakao.vectormap.route.RouteLineOptions;
import com.kakao.vectormap.route.RouteLineSegment;
import com.kakao.vectormap.route.RouteLineStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RouteLineChangeDemoActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private RouteLine routeLine;
    private RouteParseTask parseTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_line_change);
        setTitle("RouteLine Change Demo");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public int getZoomLevel() {
                return 7;
            }

            @Override
            public void onMapReady(KakaoMap map) {

                kakaoMap = map;
                kakaoMap.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
                    @Override
                    public void onCameraMoveEnd(KakaoMap kakaoMap, CameraPosition cameraPosition,
                                                GestureType gestureType) {
                        ((TextView) findViewById(R.id.tv_camera_state))
                                .setText("Current ZoomLevel: " + cameraPosition.getZoomLevel());
                    }
                });

                parseTask = new RouteParseTask();
                parseTask.execute(new OnPointsParseCallback() {
                    @Override
                    public void onPointsParsed(List<List<LatLng>> result) {
                        RouteLineStyle style = RouteLineStyle.from(getBaseContext(),
                                R.style.SimpleRouteLineStyle);

                        RouteLineSegment[] segments = new RouteLineSegment[8];
                        for (int i = 0; i < segments.length; i++) {
                            segments[i] = RouteLineSegment.from(result.get(i), style);
                        }

                        RouteLineLayer layer = kakaoMap.getRouteLineManager().getLayer();
                        routeLine = layer.addRouteLine(RouteLineOptions.from(segments));
                    }
                });
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_change_style_all:
                if (isChecked) {
                    routeLine.changeStyle(RouteLineStyle.from(getBaseContext(),
                            R.style.BlueRouteArrowLineStyle));
                } else {
                    routeLine.changeStyle(RouteLineStyle.from(getBaseContext(),
                            R.style.SimpleRouteLineStyle));
                }
                break;
            case R.id.ck_change_segments:
                if (isChecked) {
                    RouteLineStyle style1 = RouteLineStyle.from(getBaseContext(),
                            R.style.RedRouteLineStyle);
                    RouteLineStyle style2 = RouteLineStyle.from(getBaseContext(),
                            R.style.BlueRouteArrowLineStyle);

                    List<RouteLineSegment> segments = routeLine.getSegments();
                    segments.get(0).setStyles(style1);
                    segments.get(1).setStyles(style2);
                    segments.get(2).setStyles(style2);
                    segments.get(3).setStyles(style2);
                    segments.get(5).setStyles(style2);
                    routeLine.changeSegments(segments);
                } else {
                    RouteLineStyle style = RouteLineStyle.from(getBaseContext(),
                            R.style.SimpleRouteLineStyle);

                    List<RouteLineSegment> segments = routeLine.getSegments();
                    segments.get(0).setStyles(style);
                    segments.get(1).setStyles(style);
                    segments.get(2).setStyles(style);
                    segments.get(3).setStyles(style);
                    segments.get(5).setStyles(style);
                    routeLine.changeSegments(segments);
                }
                break;
        }
    }

    public interface OnPointsParseCallback {
        void onPointsParsed(List<List<LatLng>> result);
    }

    public class RouteParseTask extends ThreadTask<OnPointsParseCallback, List<List<LatLng>>> {

        private OnPointsParseCallback callback;

        @Override
        protected List<List<LatLng>> doInBackground(OnPointsParseCallback callback) {
            this.callback = callback;

            List<List<LatLng>> result = new ArrayList<>();

            String[] ptsArray = new String[] {
                    Route.PTS_1, Route.PTS_2, Route.PTS_3, Route.PTS_4, Route.PTS_5, Route.PTS_6,
                    Route.PTS_7, Route.PTS_8, Route.PTS_9, Route.PTS_10, Route.PTS_11, Route.PTS_12,
                    Route.PTS_13, Route.PTS_14, Route.PTS_15, Route.PTS_16, Route.PTS_17,
                    Route.PTS_18, Route.PTS_19, Route.PTS_20 };
            result.add(parsePoints(ptsArray));

            ptsArray = new String[] { Route.PTS_21, Route.PTS_22 };
            result.add(parsePoints(ptsArray));

            ptsArray = new String[] { Route.PTS_23 };
            result.add(parsePoints(ptsArray));

            ptsArray = new String[] { Route.PTS_24, Route.PTS_25 };
            result.add(parsePoints(ptsArray));

            ptsArray = new String[] { Route.PTS_26, Route.PTS_27, Route.PTS_28 };
            result.add(parsePoints(ptsArray));

            ptsArray = new String[] { Route.PTS_29 };
            result.add(parsePoints(ptsArray));

            ptsArray = new String[] { Route.PTS_30, Route.PTS_31, Route.PTS_32, Route.PTS_33,
                    Route.PTS_34 };
            result.add(parsePoints(ptsArray));

            ptsArray = new String[] { Route.PTS_35, Route.PTS_36, Route.PTS_37 };
            result.add(parsePoints(ptsArray));

            return result;
        }

        private List<LatLng> parsePoints(String[] ptsArray) {
            List<LatLng> result = new ArrayList<>();
            ArrayList<String> strPoints = new ArrayList<>();

            for (String pts : ptsArray) {
                Collections.addAll(strPoints, pts.split("\\|"));
            }

            for (int i = 0; i < strPoints.size(); i++) {
                String[] xys = strPoints.get(i).split(",");
                if (xys.length == 2) {
                    LatLng latLng = CoordConverter.toLatLngFromWCONG(
                            Integer.valueOf(xys[0]), Integer.valueOf(xys[1]));
                    result.add(latLng);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<List<LatLng>> result) {
            if (callback != null) {
                callback.onPointsParsed(result);
            }
        }
    }
}
