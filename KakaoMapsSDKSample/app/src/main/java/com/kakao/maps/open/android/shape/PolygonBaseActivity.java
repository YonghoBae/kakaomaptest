package com.kakao.maps.open.android.shape;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.route.GeoJsonParser;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.shape.DotPoints;
import com.kakao.vectormap.shape.MapPoints;
import com.kakao.vectormap.shape.PolygonOptions;
import com.kakao.vectormap.shape.PolygonStyle;

public class PolygonBaseActivity extends AppCompatActivity {

    private GeoJsonParser.Result[] areaResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GeoJsonParser(getBaseContext(), new GeoJsonParser.OnCompleteListener() {
            @Override
            public void onComplete(GeoJsonParser.Result[] results) {
                areaResult = results;
            }
        }).execute(new String[] {"geo_bundang_0.json", "geo_bundang_1.json", "geo_bundang_2.json",
                "geo_bundang_3.json"});
    }

    public PolygonOptions getMultiPolygonOptions(LatLng center) {
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

    public PolygonOptions[] getMapPolygons() {
        return new PolygonOptions[] { PolygonOptions.from(MapPoints.fromLatLng(areaResult[0].coordinates), PolygonStyle.from(Color.TRANSPARENT)),
                PolygonOptions.from(MapPoints.fromLatLng(areaResult[1].coordinates), PolygonStyle.from(Color.parseColor("#8098a62e"))),
                PolygonOptions.from(MapPoints.fromLatLng(areaResult[2].coordinates), PolygonStyle.from(Color.parseColor("#803cbceb"))) };
    }
}
