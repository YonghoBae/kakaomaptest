package com.kakao.maps.open.android.label;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.maps.open.android.R;
import com.kakao.maps.open.android.Route;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.animation.Interpolation;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.Badge;
import com.kakao.vectormap.label.BadgeOptions;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelAnimator;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;
import com.kakao.vectormap.label.LabelTextStyle;
import com.kakao.vectormap.label.LabelTransition;
import com.kakao.vectormap.label.PathOptions;
import com.kakao.vectormap.label.PolylineLabel;
import com.kakao.vectormap.label.PolylineLabelOptions;
import com.kakao.vectormap.label.Transition;
import com.kakao.vectormap.label.animation.DropAnimation;
import com.kakao.vectormap.label.animation.ScaleAlphaAnimation;
import com.kakao.vectormap.label.animation.ScaleAlphaAnimations;

public class LabelOverviewActivity extends AppCompatActivity {

    private int duration = 500;
    private KakaoMap kakaoMap;
    private LabelLayer labelLayer;
    private Label moveLabel;
    private PolylineLabel polylineLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_overview);
        setTitle("Label Overview");

        MapView mapView = findViewById(R.id.map_view);
        mapView.start(new KakaoMapReadyCallback() {

            @Override
            public LatLng getPosition() {
                return LatLng.from(37.393865,127.115795);
            }

            @Override
            public void onMapReady(KakaoMap map) {
                kakaoMap = map;
                labelLayer = kakaoMap.getLabelManager().getLayer();
                kakaoMap.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
                    @Override
                    public void onCameraMoveEnd(@NonNull KakaoMap kakaoMap,
                                                @NonNull CameraPosition cameraPosition,
                                                @NonNull GestureType gestureType) {

                        ((TextView) findViewById(R.id.tv_camera_state))
                                .setText("ZoomLevel: " + cameraPosition.getZoomLevel());
                    }
                });
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_icon_only:
                if (isChecked) {
                    showIconLabel("iconLabel");
                } else {
                    labelLayer.remove(labelLayer.getLabel("iconLabel"));
                }
                break;
            case R.id.ck_text_only:
                if (isChecked) {
                    showTextLabel("textLabel");
                } else {
                    labelLayer.remove(labelLayer.getLabel("textLabel"));
                }
                break;
            case R.id.ck_icon_text:
                if (isChecked) {
                    showIconTextLabel("iconTextLabel");
                } else {
                    labelLayer.remove(labelLayer.getLabel("iconTextLabel"));
                }
                break;
            case R.id.ck_by_level:
                if (isChecked) {
                    showLabelByZoomLevel("byLevelLabel");
                } else {
                    labelLayer.remove(labelLayer.getLabel("byLevelLabel"));
                }
                break;
            case R.id.ck_with_badge:
                if (isChecked) {
                    showBadgeLabel("badgeLabel");
                } else {
                    labelLayer.remove(labelLayer.getLabel("badgeLabel"));
                }
                break;
            case R.id.ck_move_label:
                if (isChecked) {
                    showMovableLabel("moveLabel");
                } else {
                    labelLayer.remove(labelLayer.getLabel("moveLabel"));
                }
                break;
            case R.id.ck_anim_label:
                if (isChecked) {
                    showAnimationLabel("dropAnimator");
                } else {
                    kakaoMap.getLabelManager().removeAnimator("dropAnimator");
                }
                break;
            case R.id.ck_wave_text:
                if (isChecked) {
                    showPolylineLabel("polylineLabel");
                } else {
                    labelLayer.remove(labelLayer.getPolylineLabel("polylineLabel"));
                }
                break;

            case R.id.ck_animations:
                if (isChecked) {
                    showSharingTransformLabel("waveAnimationLabel");
                } else {
                    kakaoMap.getLabelManager().removeAnimator("waveAnimationLabel");
                }
                break;
        }
    }

    private void showIconLabel(String labelId) {
        LatLng pos = LatLng.from(37.394660,127.111182);

        // 라벨 스타일 생성
        LabelStyles styles = kakaoMap.getLabelManager()
                .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.pink_marker)
                        .setIconTransition(LabelTransition.from(Transition.None, Transition.None))));

        // 라벨 생성
        labelLayer.addLabel(LabelOptions.from(labelId, pos).setStyles(styles));

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 15),
                CameraAnimation.from(duration));
    }

    private void showTextLabel(String labelId) {
        LatLng pos = LatLng.from(37.397437,127.118135);

        LabelStyles styles = kakaoMap.getLabelManager()
                .addLabelStyles(LabelStyles.from(LabelStyle.from().setTextStyles(
                        LabelTextStyle.from(getBaseContext(), R.style.labelTextStyle_1),
                        LabelTextStyle.from(getBaseContext(), R.style.labelTextStyle_2))
                        .setIconTransition(LabelTransition.from(Transition.None, Transition.None))));

        labelLayer.addLabel(LabelOptions.from(labelId, pos).setStyles(styles)
                .setTexts("★맛있는 치킨★", "123-4567",
                        "(\uD83C\uDF08\uD83D\uDC25\uD83C\uDF7B\uD83C\uDF57)"));

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 15),
                CameraAnimation.from(duration));
    }

    private void showIconTextLabel(String labelId) {
        LatLng pos = LatLng.from(37.392160,127.119754);

        LabelStyles styles = kakaoMap.getLabelManager()
                .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.pink_marker).setTextStyles(
                        LabelTextStyle.from(getBaseContext(), R.style.labelTextStyle_1),
                        LabelTextStyle.from(getBaseContext(), R.style.labelTextStyle_2))
                        .setIconTransition(LabelTransition.from(Transition.None, Transition.None))));

        labelLayer.addLabel(LabelOptions.from(labelId, pos).setStyles(styles)
                .setTexts("★맛있는 치킨★", "123-4567",
                        "(\uD83C\uDF08\uD83D\uDC25\uD83C\uDF7B\uD83C\uDF57)"));

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 15),
                CameraAnimation.from(duration));
    }

    private void showLabelByZoomLevel(String labelId) {
        LatLng pos = LatLng.from(37.397298,127.113719);

        LabelStyles styles = kakaoMap.getLabelManager().addLabelStyles(LabelStyles.from(
                LabelStyle.from(R.drawable.pink_marker).setZoomLevel(8),
                LabelStyle.from(R.drawable.yellow_marker).setZoomLevel(11),
                LabelStyle.from(R.drawable.blue_marker).setTextStyles(32, Color.BLACK).setZoomLevel(13),
                LabelStyle.from(R.drawable.green_marker).setZoomLevel(16)));

        labelLayer.addLabel(LabelOptions.from(labelId, pos).setStyles(styles)
                .setTexts("★맛있는 치킨★", "123-4567",
                        "(\uD83C\uDF08\uD83D\uDC25\uD83C\uDF7B\uD83C\uDF57)"));

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 15),
                CameraAnimation.from(duration));
    }

    private void showBadgeLabel(String labelId) {
        LatLng pos = LatLng.from(37.394265, 127.108332);

        Label label = labelLayer.addLabel(LabelOptions.from(labelId, pos)
                .setStyles(R.drawable.yellow_marker));

        // 라벨에 Badge 추가. 여러개 추가 가능하다. Badge 는 추가와 동시에 바로 보여진다.
        Badge[] badges = label.addBadge(BadgeOptions.from(R.drawable.bookmark_01),
                BadgeOptions.from(R.drawable.bookmark_02).setOffset(0.9f, 0.2f));
        for (Badge badge : badges) {
            badge.show();
        }

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 15),
                CameraAnimation.from(duration));
    }

    private void showMovableLabel(String labelId) {
        LatLng pos = LatLng.from(37.401750, 127.109656);

        Label moveLabel = labelLayer.addLabel(LabelOptions.from(labelId, pos)
                .setStyles(R.drawable.bus));

        moveLabel.moveOnPath(PathOptions.fromPath(Route.toGangnam).setDuration(10000), true);

        kakaoMap.moveCamera(CameraUpdateFactory
                .newCenterPosition(LatLng.from(37.394660,127.111182),
                        14), CameraAnimation.from(duration));
    }

    private void showAnimationLabel(String animatorId) {
        LatLng pos = LatLng.from(37.394660,127.111182);

        Label label = labelLayer.addLabel(LabelOptions.from(pos)
                .setStyles(R.drawable.bus));

        // 애니메이션 설정
        DropAnimation dropAnimation = DropAnimation.from(animatorId);
        dropAnimation.setRemoveLabelAtStop(true);
        dropAnimation.setPixelHeight(500);
        dropAnimation.setInterpolation(Interpolation.Linear);
        dropAnimation.setRepeatCount(3);

        // 애니메이션 생성
        LabelAnimator animator = kakaoMap.getLabelManager().addAnimator(dropAnimation);

        // 애니메이터에 라벨 추가
        animator.addLabels(label);

        // 애니메이션 시작
        animator.start();

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 14),
                CameraAnimation.from(duration));
    }

    private void showPolylineLabel(String labelId) {
        labelLayer.addPolylineLabel(PolylineLabelOptions.from(labelId,"흐르는글씨테스트",
                        LatLng.from(37.406960, 127.115587),
                        LatLng.from(37.405622, 127.115562),
                        LatLng.from(37.405026,127.115757),
                        LatLng.from(37.404516,127.115982),
                        LatLng.from(37.404133,127.116392),
                        LatLng.from(37.403715,127.116665),
                        LatLng.from(37.403458,127.116766),
                        LatLng.from(37.403168,127.116829),
                        LatLng.from(37.402896,127.116871),
                        LatLng.from(37.399865,127.116888))
                .setStyles(30, Color.BLACK));

        kakaoMap.moveCamera(CameraUpdateFactory
                .newCenterPosition(LatLng.from(37.405026,127.115757),
                        16), CameraAnimation.from(duration));
    }

    private void showSharingTransformLabel(String animatorId) {
        LatLng pos = LatLng.from(37.394660,127.111182);

        // 1. 애니메이션용 라벨 생성 - 둥근 이미지를 넣어 스타일 생성
        Label waveAnimationLabel = labelLayer.addLabel(LabelOptions.from(pos).setRank(100)
                .setStyles(LabelStyle.from(R.drawable.circle).setAnchorPoint(0.5f, 0.5f)
                        .setIconTransition(LabelTransition.from(Transition.None, Transition.None))));

        // 2. 현위치용 라벨 생성
        Label currentPosLabel = labelLayer.addLabel(LabelOptions.from(pos).setRank(101)
                .setStyles(LabelStyle.from(R.drawable.red_dot_marker)
                        .setAnchorPoint(0.5f, 0.5f)
                        .setIconTransition(LabelTransition.from(Transition.None, Transition.None))));

        // 3. 현위치용 라벨의 transform 을 애니메이션용 라벨과 공유하도록 연결
        currentPosLabel.addShareTransform(waveAnimationLabel);

        // 4. ScaleAlphaAnimation 설정 - (0.0f ~ 1.0f)
        ScaleAlphaAnimations waveAnimation = ScaleAlphaAnimations.from(animatorId);
        waveAnimation.setInitAlpha(0.0f).setInitScale(0.1f, 0.1f);
        waveAnimation.setResetToInitialState(false);
        waveAnimation.setHideLabelAtStop(true);
        waveAnimation.setRemoveLabelAtStop(true);
        waveAnimation.addScaleAlphaAnimation(ScaleAlphaAnimation.from(1.0f, 1.0f, 1.0f)
                .setInterpolation(Interpolation.CubicOut).setDuration(900));
        waveAnimation.addScaleAlphaAnimation(ScaleAlphaAnimation.from(0.1f, 0.1f, 0.0f)
                .setInterpolation(Interpolation.CubicOut).setDuration(900));
        waveAnimation.addScaleAlphaAnimation(ScaleAlphaAnimation.from(1.0f, 1.0f, 1.0f)
                .setInterpolation(Interpolation.CubicOut).setDuration(900));
        waveAnimation.addScaleAlphaAnimation(ScaleAlphaAnimation.from(0.1f, 0.1f, 0.0f)
                .setInterpolation(Interpolation.CubicOut).setDuration(900));
        waveAnimation.addScaleAlphaAnimation(ScaleAlphaAnimation.from(1.0f, 1.0f, 1.0f)
                .setInterpolation(Interpolation.CubicOut).setDuration(900));

        // 5. 설정한 ScaleAlphaAnimation 으로 LabelAnimator 를 생성하고, 라벨 추가.
        LabelAnimator waveAnimator = kakaoMap.getLabelManager().addAnimator(waveAnimation);
        waveAnimator.addLabels(waveAnimationLabel);

        // 6. LabelAnimator 시작
        waveAnimator.start();

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(pos, 15),
                CameraAnimation.from(100));
    }
}
