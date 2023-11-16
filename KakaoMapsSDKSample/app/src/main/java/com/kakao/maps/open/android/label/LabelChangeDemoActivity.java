package com.kakao.maps.open.android.label;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.label.LabelManager;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;
import com.kakao.vectormap.label.LabelTextStyle;


public class LabelChangeDemoActivity extends AppCompatActivity {

    private LabelManager labelManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_update);
        setTitle("Label Change Demo");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(final KakaoMap kakaoMap) {
                LatLng pos = kakaoMap.getCameraPosition().getPosition();
                labelManager = kakaoMap.getLabelManager();

                LabelStyles thumbsUpStyle = labelManager.addLabelStyles(
                        LabelStyles.from("thumbsUp", LabelStyle.from(R.drawable.pink_marker)));

                labelManager.addLabelStyles(LabelStyles.from("chicken",
                        LabelStyle.from(R.drawable.green_marker).setTextStyles(
                                LabelTextStyle.from(getBaseContext(), R.style.labelTextStyle_1),
                                LabelTextStyle.from(getBaseContext(), R.style.labelTextStyle_2))));

                labelManager.getLayer().addLabel(LabelOptions.from("label", pos)
                        .setStyles(thumbsUpStyle));
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        changeStyle(((CheckBox) view).isChecked());
    }

    private void changeStyle(boolean isChecked) {
        if (isChecked) {
            labelManager.getLayer().getLabel("label")
                    .changeStylesAndText(labelManager.getLabelStyles("chicken"),
                            "★맛있는 치킨★", "123-4567",
                            "(\uD83C\uDF08\uD83D\uDC25\uD83C\uDF7B\uD83C\uDF57)");
        } else {
            labelManager.getLayer().getLabel("label")
                    .changeStyles(labelManager.getLabelStyles("thumbsUp"));
        }
    }
}
