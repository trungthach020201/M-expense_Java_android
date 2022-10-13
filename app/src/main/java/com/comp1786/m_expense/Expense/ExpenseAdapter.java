package com.comp1786.m_expense.Expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;

import java.util.List;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private List<Expenses> mListExpense;

    public ExpenseAdapter(List<Expenses> ListExpense) {
    this.mListExpense=ListExpense;
    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_expense,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {
        Expenses expenses = mListExpense.get(position);
        holder.exDate.setText(expenses.getDate());
        holder.exTime.setText(expenses.getTime());
        holder.exType.setText(String.valueOf(expenses.getType_id()));
        holder.exName.setText(expenses.getName());
        holder.exLocation.setText(expenses.getLocation());
        holder.exAmount.setText(String.valueOf(expenses.getAmount()));

    }

    @Override
    public int getItemCount() {
        return mListExpense.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView exType, exName, exLocation, exAmount, exDate, exTime;
        ImageView exImage;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exDate = itemView.findViewById(R.id.ex_dateAdd_txt);
            exTime = itemView.findViewById(R.id.ex_time_txt);
            exImage = itemView.findViewById(R.id.ex_imgExpense);
            exType = itemView.findViewById(R.id.ex_expense_type_txt);
            exName = itemView.findViewById(R.id.ex_expense_name_txt);
            exLocation = itemView.findViewById(R.id.ex_addressAdd_txt);
            exAmount = itemView.findViewById(R.id.ex_expense_amount);
            linearLayout = itemView.findViewById(R.id.homeLayout);
        }
    }


}
