package com.pkolpakov.auslogics.ui;

import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkolpakov.auslogics.models.ProcessInfo;
import com.pkolpakov.auslogics.R;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Павел on 22.03.2018.
 */

public class ProcessAdapter extends RecyclerView.Adapter<ProcessAdapter.ProcessViewHolder> {
    private List<ProcessInfo> processList = Collections.emptyList();
    private OnProcessClickLister onProcessClickLister;

    public ProcessAdapter(List<ProcessInfo> processList) {
        this.processList = processList;
    }

    @Override
    public ProcessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProcessViewHolder viewHolder = new ProcessViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_process, parent, false));
        viewHolder.itemView.setOnClickListener(view1 -> {
            if (onProcessClickLister != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                onProcessClickLister.onProcessClick(processList.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProcessViewHolder holder, int position) {

        holder.textProcessName.setText(processList.get(position).getProcessName());
        holder.textPackage.setText(processList.get(position).getProcessPackage());
        try {
            holder.imageIcon.setImageDrawable(holder.imageIcon.getContext().getApplicationContext().getPackageManager().
                    getApplicationIcon(processList.get(position).getProcessPackage()));
            holder.imageIcon.setVisibility(View.VISIBLE);
        } catch (PackageManager.NameNotFoundException e) {
            holder.imageIcon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return processList.size();
    }

    static final class ProcessViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_process_name)
        TextView textProcessName;
        @BindView(R.id.text_package)
        TextView textPackage;
        @BindView(R.id.image_icon)
        ImageView imageIcon;

        ProcessViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnProcessClickLister {
        void onProcessClick(ProcessInfo processInfo);
    }
    public void setOnProcessClickLister(OnProcessClickLister onProcessClickLister) {
        this.onProcessClickLister = onProcessClickLister;
    }

}
