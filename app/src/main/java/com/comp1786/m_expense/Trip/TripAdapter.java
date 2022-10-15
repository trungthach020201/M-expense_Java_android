package com.comp1786.m_expense.Trip;

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


public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    public List<Trip> getListTrips() {
        return ListTrips;
    }

    public void setListTrips(List<Trip> listTrips) {
        ListTrips = listTrips;
        notifyDataSetChanged();
    }

    public List<Trip> ListTrips;
    private IClickItemListener iClickItemListener;

    public interface IClickItemListener{
        void onLickItemTrip(Trip trip);
    }

    public TripAdapter( List<Trip> mListTrips,IClickItemListener listenerClick) {
    this.ListTrips=mListTrips;
    this.iClickItemListener=listenerClick;
    }

    @NonNull
    @Override
    public TripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_trip,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.MyViewHolder holder, int position) {
        Trip trip = ListTrips.get(position);
        holder.idTrip.setText(position+1+"");
        holder.tripName.setText(trip.getName());
        holder.startDate.setText(trip.getStart_Date());
        holder.endDate.setText(trip.getEnd_Date());
        holder.destination.setText(trip.getDestination());
        holder.tripAmount.setText(trip.getExpenses().toString());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemListener.onLickItemTrip(trip);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListTrips.size();
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
            linearLayout = itemView.findViewById(R.id.tripItemRowLayout);
            idTrip = itemView.findViewById(R.id.idTrip);
        }
    }


}
