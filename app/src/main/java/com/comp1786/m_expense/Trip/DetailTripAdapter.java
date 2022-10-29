package com.comp1786.m_expense.Trip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.comp1786.m_expense.DatabaseHelper;

import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;


import java.util.ArrayList;

public class DetailTripAdapter extends RecyclerView.Adapter<DetailTripAdapter.MyViewHolder> {

    private final ArrayList<Expenses> mListexpenses;
    private MainActivity mMainActivity;
    Context context;

    public DetailTripAdapter(ArrayList<Expenses> listExpensesByTripId, Context context) {
        this.mListexpenses=listExpensesByTripId;
        this.context =context;
    }

    @NonNull
    @Override
    public DetailTripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_expense_trip,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailTripAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Expenses expenses = mListexpenses.get(position);

        holder.exDate.setText(expenses.getDate());
        holder.exTime.setText(expenses.getTime());
        holder.exType.setText(String.valueOf(expenses.getType_id()));
        holder.exLocation.setText(expenses.getLocation());
        holder.exAmount.setText(String.valueOf(expenses.getAmount()));

//        DatabaseHelper obj = new DatabaseHelper(context);
        holder.btndeleteExInTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete Expense?");
                builder.setMessage("Do you want to delete this expense?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        DatabaseHelper obj =new DatabaseHelper(v.getContext());
                        obj.deleteExpensesById(expenses.getId());
                        Toast.makeText(v.getContext(),"Delete Success", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                    }
                });
                builder.create().show();
            }
        });

        holder.btneditExinTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.gotoUpdateExpenseFragment(expenses);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListexpenses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView exType, exLocation, exAmount, exDate, exTime;
        ImageView exImage;
        LinearLayout linearLayout;
        ImageView btndeleteExInTrip, btneditExinTrip;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exType = itemView.findViewById(R.id.Dex_expense_type_txt);
            exLocation = itemView.findViewById(R.id.Dex_addressAdd_txt);
            exAmount = itemView.findViewById(R.id.Dex_expense_amount);
            exDate = itemView.findViewById(R.id.Dex_dateAdd_txt);
            exTime = itemView.findViewById(R.id.Dex_time_txt);
            exImage = itemView.findViewById(R.id.Dex_imgExpense);

            linearLayout = itemView.findViewById(R.id.homeLayout);
            btndeleteExInTrip = itemView.findViewById(R.id.deleteExpenseInTrip);
            btneditExinTrip = itemView.findViewById(R.id.editExpenseInTrip);
        }
    }

}
