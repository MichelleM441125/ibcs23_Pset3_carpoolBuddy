package com.example.carpoolbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class vehicleAdapter extends RecyclerView.Adapter<vehicleViewholder>
{
    ArrayList<String> vModels;
    ArrayList<String> vTypes;
    ArrayList<String> vStatus;
    ArrayList<Integer> vCapacity;
    ArrayList<Double> vPrice;
    private vehicleListener listener;


    public vehicleAdapter(ArrayList modelData, ArrayList typeData, ArrayList statusData, vehicleListener listener1)
    {
        vModels = modelData;
        vTypes = typeData;
        vStatus = statusData;
        listener = listener1;
    }


    @NonNull
    @Override
    public vehicleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_row_view, parent, false);
        vehicleViewholder holder = new vehicleViewholder(myView, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull vehicleViewholder holder, int position)
    {
        holder.modelText.setText(vModels.get(position));
        holder.typeText.setText(vTypes.get(position));
        holder.statusText.setText(vStatus .get(position));
    }

    @Override
    public int getItemCount() {
        return vModels.size();
    }

    public void newData(ArrayList models, ArrayList types, ArrayList status)
    {
        vModels = models;
        vTypes = types;
        vStatus = status;
    }

    public interface vehicleListener
    {
        void vehicleOnClick(int position);
    }
}
