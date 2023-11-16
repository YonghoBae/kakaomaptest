package com.kakao.maps.open.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private Context context;
    private List<MainListItem> items;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(MainListItem item);
    }

    public MainListAdapter(Context context, List<MainListItem> items, OnItemClickListener clickListener) {
        this.context = context;
        this.items = items;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.layout_main_list_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MainListItem item = items.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDesc());
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup parentView;
        private TextView tvTitle;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentView = itemView.findViewById(R.id.ll_parent);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_desc);
        }
    }
}
