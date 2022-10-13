package com.comp1786.m_expense.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Trip;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {


    private List<Trip> mListTrips;

    public HomeAdapter(List<Trip> trip) {
        this.mListTrips=trip;
    }



    @NonNull
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, int position) {
        Trip trip = mListTrips.get(position);
        holder.idTrip.setText(position+1+"");
        holder.tripName.setText(trip.getName());
        holder.startDate.setText(trip.getStart_Date());
        holder.endDate.setText(trip.getEnd_Date());
        holder.destination.setText(trip.getDestination());
        holder.tripAmount.setText(trip.getExpenses().toString());
    }

    @Override
    public int getItemCount() {
        return mListTrips.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tripName,startDate,endDate,destination,tripAmount,idTrip;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.trip_name_txt);
            startDate = itemView.findViewById(R.id.trip_dateStart_txt);
            endDate = itemView.findViewById(R.id.trip_dateEnd_txt);
            destination = itemView.findViewById(R.id.trip_destination_txt);
            tripAmount =itemView.findViewById(R.id.trip_amount);
            linearLayout = itemView.findViewById(R.id.homeLayout);
            idTrip = itemView.findViewById(R.id.idTrip);
        }
    }
}
