package com.example.carpoolbuddy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class vehicleViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected TextView modelText;
    protected TextView typeText;
    protected TextView statusText;
    vehicleAdapter.vehicleListener listener;


    public vehicleViewholder(@NonNull View itemView, vehicleAdapter.vehicleListener listener1) {
        super(itemView);
        modelText = itemView.findViewById(R.id.vehicleModelView);
        typeText = itemView.findViewById(R.id.vehicleTypeView);
        statusText = itemView.findViewById(R.id.vehicleCapacityView);
        listener = listener1;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.vehicleOnClick(getAdapterPosition());
    }

}
