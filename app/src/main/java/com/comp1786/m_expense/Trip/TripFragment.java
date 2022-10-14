package com.comp1786.m_expense.Trip;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Trip;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripFragment extends Fragment {
    private View mView;
    private RecyclerView rcvTrip;
    private MainActivity mMainactivity;
    private Button addTripBtn, deleteAllTripBtn;
    private ArrayList<Trip> trips;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TripFragment newInstance(String param1, String param2) {
        TripFragment fragment = new TripFragment();
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

        mView = inflater.inflate(R.layout.fragment_trip, container,false);
        rcvTrip = mView.findViewById(R.id.recycleViewTrip);

        DatabaseHelper obj =new DatabaseHelper(getContext());
        mMainactivity = (MainActivity) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainactivity);
        rcvTrip.setLayoutManager(linearLayoutManager);

        TripAdapter tripAdapter = new TripAdapter(obj.getListTrip(), new TripAdapter.IClickItemListener() {
            @Override
            public void onLickItemTrip(Trip trip) {
                mMainactivity.gotoTripDetailfragment(trip);
            }
        });
        rcvTrip.setAdapter(tripAdapter);

        addTripBtn = mView.findViewById(R.id.btnAddTrip);
        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mMainactivity.gotoAddTripFragment();
            }

        });

        deleteAllTripBtn = mView.findViewById(R.id.btnDeleteAllTrip);
        deleteAllTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteAllTrip ();
            }
        });



        SearchView searchView;
        searchView = (SearchView) mView.findViewById(R.id.searchTrip);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return mView;
    }


    void confirmDeleteAllTrip (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete All ?");
        builder.setMessage("Do you want to delete all Trip ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                DatabaseHelper obj =new DatabaseHelper(getContext());
                mMainactivity = (MainActivity) getActivity();
                obj.deleteAllTrip();
                mMainactivity.backToTripFragment();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
            }
        });
        builder.create().show();
    }
}