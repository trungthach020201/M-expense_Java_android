package com.comp1786.m_expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.comp1786.m_expense.model.Trip;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context context;
    private Fragment fragmentActivity;
    final ArrayList<Trip> trips;
//    DatabaseHelper obj = new DatabaseHelper(context.getApplicationContext());

    public HomeAdapter(ArrayList<Trip> trip) {
        this.trips=trip;
    }
//
//    public HomeAdapter(Fragment activity, Context context, ArrayList<Trip> trips) {
//        this.context=context;
//        this.fragmentActivity=activity;
//        this.trips=trips;
//    }


    @NonNull
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflate = LayoutInflater.from(context);
//       inflate.inflate(R.layout.data_row,parent,false);
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, int position) {
        holder.tripName.setText(String.valueOf(trips.get(position).getName()));
        holder.startDate.setText(String.valueOf(trips.get(position).getStart_Date()));
        holder.endDate.setText(String.valueOf(trips.get(position).getEnd_Date()));
        holder.destination.setText(String.valueOf(trips.get(position).getDestination()));
//        holder.tripAmount.setText(String.valueOf(obj.getExpensesByTripId(trips.get(position).getId())).toString());
        holder.tripAmount.setText(String.valueOf("20"));

        //update

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tripName,startDate,endDate,destination,tripAmount;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.trip_name_txt);
            startDate = itemView.findViewById(R.id.trip_dateStart_txt);
            endDate = itemView.findViewById(R.id.trip_dateEnd_txt);
            destination = itemView.findViewById(R.id.trip_destination_txt);
            tripAmount =itemView.findViewById(R.id.trip_amount);
            linearLayout = itemView.findViewById(R.id.homeLayout);
        }
    }


}
