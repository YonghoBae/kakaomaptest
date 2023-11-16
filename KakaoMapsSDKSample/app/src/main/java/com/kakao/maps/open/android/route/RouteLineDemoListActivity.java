package com.kakao.maps.open.android.route;

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

public class RouteLineDemoListActivity extends AppCompatActivity implements SubListAdapter.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list);
        setTitle("RouteLine Demo List");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        SubListAdapter adapter = new SubListAdapter();
        adapter.setOnClickListener(this);
        adapter.setItems(new SubListAdapter.SubListItem("다양한 RouteLine 소개", "다양한 스타일의 RouteLine 보기", RouteLineOverviewActivity.class),
                new SubListAdapter.SubListItem("RouteLine 변경", "RouteLine 스타일 및 세그먼트 변경하기", RouteLineChangeDemoActivity.class));

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
