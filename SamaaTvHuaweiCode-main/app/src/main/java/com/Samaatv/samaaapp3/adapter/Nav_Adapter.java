package com.Samaatv.samaaapp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.interfaces.OnClick;
import com.Samaatv.samaaapp3.model.Nav_Model;

import java.util.List;

public class Nav_Adapter extends RecyclerView.Adapter<Nav_Adapter.ViewHolder> {

    private Context context;
    private List<Nav_Model> list;
    private OnClick listener;

    public void setListener(OnClick listener) {
        this.listener = listener;
    }

    public Nav_Adapter(Context context) {
        this.context = context;
    }

    public void setList(List<Nav_Model> list) {
        this.list = list;
    }

    public void SelectOneRow(int position)
    {
        UnSelectAllRecords();
        list.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    private void UnSelectAllRecords()
    {
        for (int count = 0 ; count < list.size(); count++) {
            list.get(count).setSelected(false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.nav_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TvTitle.setText(list.get(position).getTitle());
        if (list.get(position).isSelected()) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.silver));
            holder.TvTitle.setTextColor(context.getResources().getColor(R.color.white));
        }
        else{

            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.TvTitle.setTextColor(context.getResources().getColor( R.color.colorPrimaryDark));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView TvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TvTitle = itemView.findViewById(R.id.TvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
