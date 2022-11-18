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
    private SearchView searchView;

    public static TripFragment newInstance(String param1, String param2) {
        TripFragment fragment = new TripFragment();
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
        mView = inflater.inflate(R.layout.fragment_trip, container,false);
        DatabaseHelper obj =new DatabaseHelper(getContext());
        mMainactivity = (MainActivity) getActivity();

        findObject();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainactivity);
        rcvTrip.setLayoutManager(linearLayoutManager);
        TripAdapter tripAdapter = new TripAdapter(obj.getListTrip(), new TripAdapter.IClickItemListener() {
            @Override
            public void onLickItemTrip(Trip trip) {
                mMainactivity.gotoTripDetailfragment(trip);
            }
        });
        rcvTrip.setAdapter(tripAdapter);

        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mMainactivity.gotoAddTripFragment();
            }

        });
        deleteAllTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteAllTrip ();
            }
        });

        String query=searchView.getQuery().toString();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tripAdapter.setListTrips(obj.searchTripByName(query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    tripAdapter.setListTrips(obj.getListTrip());
                }
                return true;
            }
        });

        return mView;
    }
    private void findObject(){
        rcvTrip = mView.findViewById(R.id.recycleViewTrip);
        addTripBtn = mView.findViewById(R.id.btnAddTrip);
        deleteAllTripBtn = mView.findViewById(R.id.btnDeleteAllTrip);
        searchView = (SearchView) mView.findViewById(R.id.searchTrip);
    }

    private void confirmDeleteAllTrip (){
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