package com.kakao.maps.open.android.etc;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.maps.open.android.ViewToBitmap;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapLogger;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;


public class CalloutBalloonStyleDemoActivity extends AppCompatActivity {

    private LabelLayer labelLayer;
    private CalloutBalloonStyleAdapter styleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callout_balloon_style);
        setTitle("CalloutBalloon Style Demo");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new MapLifeCycleCallback() {
            @Override
            public void onMapDestroy() {

            }

            @Override
            public void onMapError(Exception error) {
                MapLogger.e(error.getMessage());
            }
        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull KakaoMap kakaoMap) {

                // Label 클릭 리스너
                kakaoMap.setOnLabelClickListener(new KakaoMap.OnLabelClickListener() {
                    @Override
                    public void onLabelClicked(KakaoMap kakaoMap, LabelLayer layer, Label label) {

                        switch (label.getLabelId()) {
                            case "centerLabel":
                                onCenterLabelClicked();
                                break;
                            case "calloutBalloonLabel":
                                styleAdapter.notifyLabelClicked();
                                break;
                        }}
                });

                labelLayer = kakaoMap.getLabelManager().getLayer();
                createCenterLabel(kakaoMap.getCameraPosition().getPosition());

                styleAdapter = new CalloutBalloonStyleAdapter();
                styleAdapter.setLabel(labelLayer.getLabel("calloutBalloonLabel"));
            }
        });
    }

    private void createCenterLabel(LatLng centerPosition) {
        // 중심점에 라벨 추가
        Label label = labelLayer.addLabel(LabelOptions.from("centerLabel", centerPosition)
                .setStyles(LabelStyle.from(R.drawable.blue_marker)));

        // 중심점에 CalloutBalloon 라벨 추가
        Label balloonLabel = createCalloutBalloonLabel(centerPosition);
        label.addShareTransform(balloonLabel);
    }

    private Label createCalloutBalloonLabel(LatLng centerPosition) {
        Bitmap bitmap = ViewToBitmap.createBitmap(LayoutInflater.from(this)
                .inflate(R.layout.layout_callout_balloon, null));

        Label label = labelLayer.addLabel(
                LabelOptions.from("calloutBalloonLabel", centerPosition)
                        .setStyles(LabelStyle.from(bitmap))
                        .setVisible(false));

        label.changePixelOffset(0, -152);   // R.drawable.blue_marker 의 height 값

        return label;
    }

    public void onButtonClicked(View view) {
        Label calloutBalloonLabel = labelLayer.getLabel("calloutBalloonLabel");

        switch (view.getId()) {
            case R.id.btn_balloon_show:
                calloutBalloonLabel.show();
                break;
            case R.id.btn_balloon_hide:
                calloutBalloonLabel.hide();
                break;
        }
    }

    private void onCenterLabelClicked() {
        Label calloutBalloonLabel = labelLayer.getLabel("calloutBalloonLabel");
        if (calloutBalloonLabel.isShow()) {
            calloutBalloonLabel.hide();
        } else {
            calloutBalloonLabel.show();
        }
    }

    private class CalloutBalloonStyleAdapter {
        private Label label;
        private LabelStyles defaultStyles, clickedStyles;
        private boolean isClicked = false;

        public void setLabel(Label label) {
            this.label = label;
            this.defaultStyles = label.getStyles();
        }

        public void notifyLabelClicked() {
            isClicked = !isClicked;
            if (isClicked) {
                label.changeStyles(getClickedCalloutBalloonStyle());
            } else {
                label.changeStyles(getCalloutBalloonStyle());
            }
        }

        public LabelStyles getCalloutBalloonStyle() {
            if (defaultStyles == null) {
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_callout_balloon, null);
                TextView tv = (TextView) viewGroup.findViewById(R.id.tv_callout_balloon);
                tv.setText("CalloutBalloon Style!");

                defaultStyles = LabelStyles.from(LabelStyle.from(ViewToBitmap.createBitmap(viewGroup)));
            }
            return defaultStyles;
        }

        public LabelStyles getClickedCalloutBalloonStyle() {
            if (clickedStyles == null) {
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_callout_balloon, null);
                TextView tv = (TextView) viewGroup.findViewById(R.id.tv_callout_balloon);
                tv.setText("Clicked!");

                clickedStyles = LabelStyles.from(LabelStyle.from(ViewToBitmap.createBitmap(viewGroup)));
            }
            return clickedStyles;
        }
    }
}
