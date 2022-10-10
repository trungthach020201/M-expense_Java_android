package com.comp1786.m_expense;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp1786.m_expense.model.Trip;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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

        mMainactivity = (MainActivity) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainactivity);
        rcvTrip.setLayoutManager(linearLayoutManager);
        HomeAdapter homeAdapter = new HomeAdapter(getListTrip());
        rcvTrip.setAdapter(homeAdapter);

        DatabaseHelper obj =new DatabaseHelper(getContext());
        TextView totalTrip = (TextView) mView.findViewById(R.id.totalTrip);
        TextView totalExpense = (TextView) mView.findViewById(R.id.totalExpense);
        TextView totalAmount = (TextView) mView.findViewById(R.id.totalAmount);
        int totaltrip = obj.getListTrip().size();
        int totalexpense = obj.getListExpense().size();
        float totalamount = obj.getTotalExpenses();
        totalTrip.setText(totaltrip+"");
        totalExpense.setText(totalexpense+"");
        totalAmount.setText(totalamount+"");

        return mView;
    }

    private List<Trip> getListTrip() {

        List<Trip> trips= new ArrayList<>();
//        trips.add(new Trip(7,"me","me","hihi","hihi",1,"hi",1));
//        trips.add(new Trip(7,"me","me","hihi","hihi",1,"hi",1));
        return trips;
    }
}