package com.aariyan.briefcase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.briefcase.Model.ItemModel;
import com.aariyan.briefcase.R;

import java.text.DecimalFormat;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<ItemModel> list;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public ItemAdapter(Context context,List<ItemModel>list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel model = list.get(position);
        holder.qty.setText(""+model.getQty());
        holder.itemName.setText("" + model.getItemName());
        holder.price.setText("" + df.format(model.getPrice()));
        holder.tax.setText("" + df.format(model.getTax()));
        holder.total.setText("" + df.format(model.getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName,qty,price,tax,total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNames);
            qty = itemView.findViewById(R.id.qtys);
            price = itemView.findViewById(R.id.prices);
            tax = itemView.findViewById(R.id.taxs);
            total = itemView.findViewById(R.id.totals);
        }
    }
}
