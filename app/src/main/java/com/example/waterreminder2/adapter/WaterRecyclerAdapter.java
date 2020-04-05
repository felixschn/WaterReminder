package com.example.waterreminder2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waterreminder2.R;
import com.example.waterreminder2.models.Water;

import java.util.ArrayList;

public class WaterRecyclerAdapter extends RecyclerView.Adapter<WaterRecyclerAdapter.ViewHolder> {

    private ArrayList<Water> mWater = new ArrayList<>();

    public WaterRecyclerAdapter(ArrayList<Water> mWater) {
        this.mWater = mWater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_water_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.timestamp.setText(mWater.get(position).getCurrentTimeStamp());
        //TODO: prüfen ob der automatische Cast von INT zu String zu einem Fehler führt
        int currentWater = mWater.get(position).getAmount();

        holder.waterAmount.setText(Integer.toString(currentWater));

    }

    @Override
    public int getItemCount() {
        return mWater.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView waterAmount, timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            waterAmount = itemView.findViewById(R.id.water_amount);
            timestamp = itemView.findViewById(R.id.currentTimeStamp);

        }
    }
    public interface OnWaterListener{
        void onWaterClick(int position);
    }
}
