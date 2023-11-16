package com.kakao.maps.open.android.kakaomap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.maps.open.android.R;
import com.kakao.maps.open.android.SubListAdapter;


public class KakaoMapDemoListActivity extends AppCompatActivity implements SubListAdapter.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list);

        setTitle("KakaoMaps Api Demo List");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        SubListAdapter adapter = new SubListAdapter();
        adapter.setOnClickListener(this);
        adapter.setItems(new SubListAdapter.SubListItem("인증과 지도 시작", "Start, MapAuthToken, KakaoMapReadyCallback", MapStartDemoActivity.class),
                new SubListAdapter.SubListItem("지도 타입 및 오버레이", "MapType, MapOverlay", MapTypeOverlayDemoActivity.class),
                new SubListAdapter.SubListItem("지도화면 크기 및 패딩 변경", "Viewport, Padding", ViewportPaddingChangeDemoActivity.class),
                new SubListAdapter.SubListItem("스크린 위치와 좌표 간 변환", "fromScreenPoint, toScreenPoint", ScreenPointToLatLngActivity.class),
                new SubListAdapter.SubListItem("지도의 Poi 설정", "Poi Clickable, Visible, Scale, Language", PoiDemoActivity.class),
                new SubListAdapter.SubListItem("나침반, 축척 설정", "Compass, ScaleBar", CompassScaleBarActivity.class));

        RecyclerView recyclerView = findViewById(R.id.sub_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(SubListAdapter.SubListItem item) {
        if (item.activity != null) {
            startActivity(new Intent(this, item.activity));
        } else {
            Toast.makeText(getApplicationContext(), "조금만 기다려 주세요! 준비중 입니다 :)", Toast.LENGTH_SHORT).show();
        }
    }
}
