package com.gigawattstechnology.waapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToolRecycleAdapter extends RecyclerView.Adapter<ToolRecycleAdapter.RecyclerViewHolder>{

    Context context;
    String[] toolList;
    onItemClickInteface onItemClickInteface;

    public ToolRecycleAdapter(Context context, String[] toolList, com.gigawattstechnology.waapp.onItemClickInteface onItemClickInteface) {
        this.context = context;
        this.toolList = toolList;
        this.onItemClickInteface = onItemClickInteface;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.tooltab;
    }
    @NonNull
    @Override
    public ToolRecycleAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolRecycleAdapter.RecyclerViewHolder holder, int position) {
        holder.button.setText(toolList[position]);
    }

    @Override
    public int getItemCount() {
        return toolList.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.ToolButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickInteface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
