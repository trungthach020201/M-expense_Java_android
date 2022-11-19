package com.comp1786.m_expense.Expense;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.Trip.TripAdapter;
import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Trip;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseFragment extends Fragment {

    private View mView;
    private RecyclerView rcvExpense;
    private MainActivity mMainactivity;


    public ExpenseFragment() {
        // Required empty public constructor
    }

    public static ExpenseFragment newInstance(String param1, String param2) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_expense, container,false);
        loadObject();
        return mView;
    }
    public void loadObject(){
        rcvExpense = mView.findViewById(R.id.recycleViewExpense);
        DatabaseHelper obj =new DatabaseHelper(getContext());
        mMainactivity = (MainActivity) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainactivity);
        rcvExpense.setLayoutManager(linearLayoutManager);
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(obj.getListExpense(), new ExpenseAdapter.IClickItemListener() {
            @Override
            public void onLickItemExpense(Expenses expenses) {
                mMainactivity.gotoDetailExpenseFragment(expenses);
            }
        });
        rcvExpense.setAdapter(expenseAdapter);

        SearchView searchView;
        searchView = (SearchView) mView.findViewById(R.id.searchTrip);
        String query=searchView.getQuery().toString();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                expenseAdapter.setmListExpense(obj.searchExpensesByName(query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    expenseAdapter.setmListExpense(obj.getListExpense());
                }
                return true;
            }
        });
    }
}