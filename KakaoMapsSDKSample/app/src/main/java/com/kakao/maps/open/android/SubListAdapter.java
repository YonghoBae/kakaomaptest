package com.kakao.maps.open.android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.ListViewHolder> {
    private List<SubListItem> items = new ArrayList<>();
    private SubListAdapter.OnItemClickListener clickListener = null;

    public interface OnItemClickListener {
        void onItemClick(SubListAdapter.SubListItem item);
    }

    public void setItems(SubListItem... items) {
        this.items.clear();
        this.items.addAll(Arrays.asList(items));
    }

    public void setOnClickListener(SubListAdapter.OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_sub_list_item, parent, false);

        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final SubListItem item = items.get(position);
        holder.title.setText(item.title);
        holder.desc.setText(item.desc);

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public View parentView;
        public TextView title;
        public TextView desc;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            parentView = itemView.findViewById(R.id.parent_view);
            title = itemView.findViewById(R.id.tv_title);
            desc = itemView.findViewById(R.id.tv_desc);
        }
    }

    public static class SubListItem {
        public String title;
        public String desc;
        public Class<? extends Activity> activity;

        public SubListItem(String title, String desc, Class activity) {
            this.title = title;
            this.desc = desc;
            this.activity = activity;
        }
    }
}
