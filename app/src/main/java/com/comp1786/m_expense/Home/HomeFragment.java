package com.comp1786.m_expense.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private View mView;
    private RecyclerView rcvTrip;
    private MainActivity mMainactivity;



    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container,false);
        rcvTrip = mView.findViewById(R.id.recycleViewHome);
        DatabaseHelper obj =new DatabaseHelper(getContext());
        mMainactivity = (MainActivity) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainactivity);
        rcvTrip.setLayoutManager(linearLayoutManager);
//        Expenses expenses=new Expenses(1,1,20.2f,"none","none","none","none","none","none",1);
//        obj.addExpense(expenses);
        HomeAdapter homeAdapter = new HomeAdapter(obj.getListTrip());
        rcvTrip.setAdapter(homeAdapter);

        TextView totalTrip = (TextView) mView.findViewById(R.id.totalTrip);
        TextView totalExpense = (TextView) mView.findViewById(R.id.totalExpense);
        TextView totalAmount = (TextView) mView.findViewById(R.id.totalAmount);
//        Trip trip=new Trip(1,"none","none","none","none",1,"none",1);
//        obj.addTrip(trip);
        int totaltrip = obj.getListTrip().size();
        int totalexpense = obj.getListExpense().size();
        float totalamount = obj.getTotalExpenses();
        totalTrip.setText(totaltrip+"");
        totalExpense.setText(totalexpense+"");
        Locale locale = new Locale("vi", "VN");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        totalAmount.setText(formatter.format(totalamount));

        return mView;
    }

}