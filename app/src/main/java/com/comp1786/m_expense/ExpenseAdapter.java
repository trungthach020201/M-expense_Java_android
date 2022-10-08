package com.comp1786.m_expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private Context context;
    String[] list;

    public ExpenseAdapter(String[] list) {
    this.list=list;
    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflate = LayoutInflater.from(context);
//       inflate.inflate(R.layout.data_row,parent,false);
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_expense,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.length;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }


}
