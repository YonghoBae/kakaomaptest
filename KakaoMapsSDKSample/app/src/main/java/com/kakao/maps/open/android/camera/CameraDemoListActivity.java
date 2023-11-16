package com.kakao.maps.open.android.camera;

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

public class CameraDemoListActivity extends AppCompatActivity implements SubListAdapter.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list);

        setTitle("Camera Demo List");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        SubListAdapter adapter = new SubListAdapter();
        adapter.setOnClickListener(this);
        adapter.setItems(new SubListAdapter.SubListItem("기본적인 카메라 이동",
                        "CameraUpdateFactory, moveCamera", CameraUpdateDemoActivity.class),
                new SubListAdapter.SubListItem("특정 좌표들이 나오도록 카메라 이동",
                        "fitMapPoints, canShowMapPoints", CameraFitPointsDemoActivity.class));

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
