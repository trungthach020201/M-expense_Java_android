package com.comp1786.m_expense.Expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.Trip.TripAdapter;
import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Trip;
import com.comp1786.m_expense.model.Type;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private List<Expenses> mListExpense;
    DatabaseHelper object;

    private ExpenseAdapter.IClickItemListener iClickItemListener;

    public interface IClickItemListener{
        void onLickItemExpense(Expenses expenses);
    }
    public ExpenseAdapter(List<Expenses> ListExpense, IClickItemListener listenerClick) {
    this.mListExpense=ListExpense;
    this.iClickItemListener=listenerClick;
    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_expense,parent,false);
        object=new DatabaseHelper(view.getContext());
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_expense,parent,false));
    }

    public List<Expenses> getmListExpense() {
        return mListExpense;
    }

    public void setmListExpense(List<Expenses> mListExpense) {
        notifyDataSetChanged();
        this.mListExpense = mListExpense;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {
        Expenses expenses = mListExpense.get(position);
        holder.exDate.setText(expenses.getDate());
        holder.exTime.setText(expenses.getTime());
        Type type=object.searchTypeById(expenses.getType_id());
        holder.exType.setText(type.getName());
        holder.exName.setText(expenses.getName());
        holder.exLocation.setText(expenses.getLocation());
        Locale locale = new Locale("vi", "VN");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        holder.exAmount.setText(formatter.format(expenses.getAmount()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemListener.onLickItemExpense(expenses);
            }
        });

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
