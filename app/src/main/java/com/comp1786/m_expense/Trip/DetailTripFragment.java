package com.comp1786.m_expense.Trip;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.Home.HomeAdapter;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Trip;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailTripFragment extends Fragment {

    private View mView;
    private RecyclerView rcvDetailTrip;
    private MainActivity mMainactivity;
    TextView tripDestination, tripAmount, tripStartDate, tripEndDate, tripType;
    ImageButton btnDelete, btnedit;
    private ArrayList<Trip> trips;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailTripFragment newInstance(String param1, String param2) {
        DetailTripFragment fragment = new DetailTripFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail_trip, container,false);
        rcvDetailTrip = mView.findViewById(R.id.recycleDetailTrip);
        DatabaseHelper obj =new DatabaseHelper(getContext());
        mMainactivity = (MainActivity) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainactivity);
        rcvDetailTrip.setLayoutManager(linearLayoutManager);

        Bundle bundleReceive = getArguments();
        Trip trip = (Trip) bundleReceive.get("object_trip");

        DetailTripAdapter detailTripAdapter = new DetailTripAdapter();
        rcvDetailTrip.setAdapter(detailTripAdapter);

        tripDestination = mView.findViewById(R.id.tripDestinationD);
        tripAmount = mView.findViewById(R.id.tripAmountD);
        tripStartDate = mView.findViewById(R.id.tripStartDateD);
        tripEndDate = mView.findViewById(R.id.tripEndDateD);
        tripType = mView.findViewById(R.id.tripTypeD);

        tripDestination.setText(trip.getDestination());
        tripAmount.setText(String.valueOf(trip.getExpenses()));
        tripStartDate.setText(trip.getStart_Date());
        tripEndDate.setText(trip.getEnd_Date());
        String type;
        if(trip.getType() != 1){
            type ="internal";
        }else type="external";
        tripType.setText(type);

        return mView;

    }

}