package com.kakao.maps.open.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.maps.open.android.camera.CameraDemoListActivity;
import com.kakao.maps.open.android.dimscreen.DimScreenOverviewActivity;
import com.kakao.maps.open.android.etc.EtcDemoListActivity;
import com.kakao.maps.open.android.kakaomap.KakaoMapDemoListActivity;
import com.kakao.maps.open.android.label.LabelDemoListActivity;
import com.kakao.maps.open.android.mapwidget.InfoWindowOverviewActivity;
import com.kakao.maps.open.android.route.RouteLineDemoListActivity;
import com.kakao.maps.open.android.shape.ShapeDemoListActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainListAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView view = findViewById(R.id.rc_main_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        view.setLayoutManager(layoutManager);
        view.addItemDecoration(new ListItemDecoration(2, 50, true));

        List<MainListItem> items = new ArrayList<>();
        items.add(new MainListItem("KakaoMap", "다양한 옵션으로 지도 시작, 크기 등 설정하기", KakaoMapDemoListActivity.class));
        items.add(new MainListItem("Camera", "카메라 확대/축소, 방향, 각도 설정을 통해 지도 움직이기", CameraDemoListActivity.class));
        items.add(new MainListItem("Label", "지도에 Label 그리기", LabelDemoListActivity.class));
        items.add(new MainListItem("Shape", "지도에 Polygon, Polyline 그리기", ShapeDemoListActivity.class));
        items.add(new MainListItem("DimScreen", "지도에 DimScreen 나타내기", DimScreenOverviewActivity.class));
        items.add(new MainListItem("RouteLine", "지도에 RouteLine 그리기", RouteLineDemoListActivity.class));
        items.add(new MainListItem("MapWidget", "지도에 InfoWindow 그리기 ", InfoWindowOverviewActivity.class));
        items.add(new MainListItem("Etc", "기타 다양한 지도 활용하기 ", EtcDemoListActivity.class));
        
        MainListAdapter adapter = new MainListAdapter(this, items, this);
        view.setAdapter(adapter);
    }

    @Override
    public void onItemClick(MainListItem item) {
        if (item.getActivity() != null) {
            startActivity(new Intent(this, item.getActivity()));
        } else {
            Toast.makeText(getApplicationContext(),
                    "조금만 기다려 주세요! 준비중 입니다.", Toast.LENGTH_SHORT).show();
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        menu.findItem(R.id.action_hash).setTitle(BuildConfig.ENGINE_HASH);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }
}