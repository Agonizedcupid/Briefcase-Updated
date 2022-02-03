package com.aariyan.briefcase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.briefcase.Activity.LogVisit;
import com.aariyan.briefcase.Activity.ProblematicItemActivity;
import com.aariyan.briefcase.Model.DailyScheduleModel;
import com.aariyan.briefcase.R;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Context context;
    private List<DailyScheduleModel> list;

    public ScheduleAdapter(Context context, List<DailyScheduleModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_schedule_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyScheduleModel model = list.get(position);
        holder.date.setText(model.getDate());
        holder.name.setText(model.getStoreName());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                context.startActivity(new Intent(context, LogVisit.class)
                        .putExtra("name",model.getStoreName())
                        .putExtra("code",model.getCode())
                );

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cNames);
            date = itemView.findViewById(R.id.dates);
        }
    }
}
