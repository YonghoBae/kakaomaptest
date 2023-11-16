package com.kakao.maps.open.android.label;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelLayerOptions;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;
import com.kakao.vectormap.label.OrderingType;


public class LabelRankDemoActivity extends AppCompatActivity {

    private LabelLayer labelLayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_rank);
        setTitle("Label Rank Demo");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @NonNull
            @Override
            public int getZoomLevel() {
                return 18;
            }

            @NonNull
            @Override
            public LatLng getPosition() {
                return LatLng.from(37.394760, 127.111192);
            }

            @Override
            public void onMapReady(KakaoMap map) {
                labelLayer = map.getLabelManager().addLayer(LabelLayerOptions.from()
                        .setOrderingType(OrderingType.Rank));

                labelLayer.addLabel(LabelOptions.from("yellow",
                                LatLng.from(37.394960, 127.111282))
                        .setStyles(LabelStyle.from(getRankBitmap(0, R.drawable.yellow_marker))
                                .setApplyDpScale(false)));

                labelLayer.addLabel(LabelOptions.from("green",
                                LatLng.from(37.394860, 127.111382))
                        .setStyles(LabelStyle.from(getRankBitmap(0, R.drawable.green_marker))
                                .setApplyDpScale(false)));

                labelLayer.addLabel(LabelOptions.from("pink",
                                LatLng.from(37.394760, 127.111192))
                        .setStyles(LabelStyle.from(getRankBitmap(0, R.drawable.pink_marker))
                                .setApplyDpScale(false)));

                labelLayer.addLabel(LabelOptions.from("blue",
                                LatLng.from(37.394560, 127.111292))
                        .setStyles(LabelStyle.from(getRankBitmap(0, R.drawable.blue_marker))
                                .setApplyDpScale(false)));
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_rank:
                if (isChecked) {
                    updateLabel("yellow", 0, R.drawable.yellow_marker);
                    updateLabel("green", 1, R.drawable.green_marker);
                    updateLabel("pink", 2, R.drawable.pink_marker);
                    updateLabel("blue", 3, R.drawable.blue_marker);
                } else {
                    updateLabel("yellow", 0, R.drawable.yellow_marker);
                    updateLabel("green", 0, R.drawable.green_marker);
                    updateLabel("pink", 0, R.drawable.pink_marker);
                    updateLabel("blue", 0, R.drawable.blue_marker);
                }
                break;
        }
    }

    private void updateLabel(String labelId, int rank, int markerResId) {
        labelLayer.getLabel(labelId).changeRank(rank);
        labelLayer.getLabel(labelId).changeStyles(LabelStyles.from(
                LabelStyle.from(getRankBitmap(rank, markerResId)).setApplyDpScale(false)));
    }

    private Bitmap getRankBitmap(float rank, int bgResId) {
        View rankView = LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_rank_label, null);
        rankView.setBackgroundResource(bgResId);
        ((TextView) rankView.findViewById(R.id.tv_rank)).setText("Rank\n" + (int)rank);

        int width = convertDpToPixels(convertPixelToDp(113 * 2));
        int height = convertDpToPixels(convertPixelToDp(152 * 2));
        return createBitmap(rankView, width, height);
    }

    private Bitmap createBitmap(View view, int width, int height) {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
            view.draw(canvas);
        }
        return bitmap;
    }

    private float convertPixelToDp(int px) {
        float density = getBaseContext().getResources().getDisplayMetrics().density;

        if (density == 1.0) {
            density *= 4.0;
        } else if (density == 1.5) {
            density *= (8 / 3);
        } else if (density == 2.0) {
            density *= 2.0;
        }

        return px / density;
    }

    private int convertDpToPixels(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
