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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private View mView;
    private RecyclerView rcvTrip;
    private MainActivity mMainactivity;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        totalAmount.setText(String.format("%.2f", totalamount));

        return mView;
    }

}