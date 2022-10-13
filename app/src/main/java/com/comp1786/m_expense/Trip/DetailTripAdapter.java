package com.comp1786.m_expense.Trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comp1786.m_expense.Home.HomeAdapter;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;

import java.util.ArrayList;

public class DetailTripAdapter extends RecyclerView.Adapter<DetailTripAdapter.MyViewHolder> {

    private ArrayList<Expenses> mListexpenses;


    public DetailTripAdapter(ArrayList<Expenses> listExpensesByTripId) {
        this.mListexpenses=listExpensesByTripId;
    }

    @NonNull
    @Override
    public DetailTripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_expense_trip,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailTripAdapter.MyViewHolder holder, int position) {
        Expenses expenses = mListexpenses.get(position);
        holder.exDate.setText(expenses.getDate().toString());
        holder.exTime.setText(expenses.getTime().toString());
        holder.exType.setText(String.valueOf(expenses.getType_id()));
        holder.exLocation.setText(expenses.getLocation().toString());
        holder.exAmount.setText(String.valueOf(expenses.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mListexpenses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView exType, exLocation, exAmount, exDate, exTime;
        ImageView exImage;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exType = itemView.findViewById(R.id.Dex_expense_type_txt);
            exLocation = itemView.findViewById(R.id.Dex_addressAdd_txt);
            exAmount = itemView.findViewById(R.id.Dex_expense_amount);
            exDate = itemView.findViewById(R.id.Dex_dateAdd_txt);
            exTime = itemView.findViewById(R.id.Dex_time_txt);
            exImage = itemView.findViewById(R.id.Dex_imgExpense);

            linearLayout = itemView.findViewById(R.id.homeLayout);

        }
    }
}
