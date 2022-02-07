package com.example.carpoolbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class bookVehicleAdapter extends RecyclerView.Adapter<bookVehicleViewholder>
{
    ArrayList<String> vModels;
    ArrayList<String> vTypes;
    //ArrayList<Integer> vCapacity;
    ArrayList<String> vPrice;
    private bookVehicleListener bookListener;


    public bookVehicleAdapter(ArrayList modelData, ArrayList typeData, ArrayList priceData,
                              bookVehicleListener bookListener1)
    {
        vModels = modelData;
        vTypes = typeData;
        //vCapacity = capacityData;
        vPrice = priceData;
        bookListener = bookListener1;

    }


    @NonNull
    @Override
    public bookVehicleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_vehicle_row_view, parent, false);
        bookVehicleViewholder holder = new bookVehicleViewholder(myView, bookListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bookVehicleViewholder holder, int position)
    {
        holder.modelText.setText(vModels.get(position));
        holder.typeText.setText(vTypes.get(position));
        //holder.capacityText.setText(vCapacity.get(position));
        holder.priceText.setText(vPrice.get(position));
    }

    @Override
    public int getItemCount() {
        return vModels.size();
    }

    public void newData(ArrayList models, ArrayList types, ArrayList prices)
    {
        vModels = models;
        vTypes = types;
        //vCapacity = capacities;
        vPrice = prices;
    }

    public interface bookVehicleListener
    {
        void bookVehicleOnClick(int position);
    }

}
