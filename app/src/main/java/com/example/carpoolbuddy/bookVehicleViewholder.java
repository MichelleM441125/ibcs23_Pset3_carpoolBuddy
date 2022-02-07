package com.example.carpoolbuddy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolbuddy.R;

public class bookVehicleViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected TextView modelText;
    protected TextView typeText;
    //protected TextView capacityText;
    protected TextView priceText;
    bookVehicleAdapter.bookVehicleListener bookListener;

    public bookVehicleViewholder(@NonNull View myView, bookVehicleAdapter.bookVehicleListener bookListener1)
    {
        super(myView);
        modelText = myView.findViewById(R.id.vehicleModelView);
        typeText = myView.findViewById(R.id.vehicleTypeView);
        //capacityText = myView.findViewById(R.id.vehicleCapacityView);
        priceText = myView.findViewById(R.id.vehiclePriceView);
        bookListener = bookListener1;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        bookListener.bookVehicleOnClick(getAdapterPosition());
    }
}
