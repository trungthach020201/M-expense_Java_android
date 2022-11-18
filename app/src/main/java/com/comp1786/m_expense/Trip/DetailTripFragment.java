package com.comp1786.m_expense.Trip;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.Home.HomeAdapter;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Trip;
import com.google.android.material.tabs.TabLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailTripFragment extends Fragment {

    private View mView;
    private RecyclerView rcvDetailTrip;
    private MainActivity mMainactivity;

    Button addNewEx;
    TextView tripDestination, tripAmount, tripStartDate, tripEndDate, tripType;
    ImageButton btnDeleteByID, btnedit;

    public static DetailTripFragment newInstance(String param1, String param2) {
        DetailTripFragment fragment = new DetailTripFragment();
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
        Bundle bundleReceive = getArguments();
        Trip trip = (Trip) bundleReceive.get("object_trip");
        ArrayList<Expenses> expenses=new ArrayList<>();
        mView = inflater.inflate(R.layout.fragment_detail_trip, container,false);
        DatabaseHelper obj =new DatabaseHelper(getContext());
        mMainactivity = (MainActivity) getActivity();
        findObject();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainactivity);
        rcvDetailTrip.setLayoutManager(linearLayoutManager);
        DetailTripAdapter detailTripAdapter = new DetailTripAdapter(obj.getListExpensesByTripId(trip.getId()),new DetailTripAdapter.IClickItemListener(){
            @Override
            public void onLickItemTrip(Expenses expenses) {
                mMainactivity.gotoUpdateExpenseFragment(expenses);
            }
        }, getContext());
        rcvDetailTrip.setAdapter(detailTripAdapter);
        setTextTrip(trip);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainactivity.gotoUpdateTripFragment(trip);
            }
        });

        btnDeleteByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteTripByID();
            }
        });

        addNewEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainactivity.goToAddExpenseFragment (trip.getId());
            }
        });

        return mView;

    }
    private void findObject(){
        addNewEx = mView.findViewById(R.id.btnAddNewExInTrip);
        btnDeleteByID = mView.findViewById(R.id.deleteTrip);
        rcvDetailTrip = mView.findViewById(R.id.recycleDetailTrip);
        btnedit = mView.findViewById(R.id.EditTrip);
        tripDestination = mView.findViewById(R.id.tripDestinationD);
        tripAmount = mView.findViewById(R.id.tripAmountD);
        tripStartDate = mView.findViewById(R.id.tripStartDateD);
        tripEndDate = mView.findViewById(R.id.tripEndDateD);
        tripType = mView.findViewById(R.id.tripTypeD);
    }
    private void setTextTrip(Trip trip){

        tripDestination.setText(trip.getDestination());
        Locale locale = new Locale("vi", "VN");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        tripAmount.setText(String.format(formatter.format(trip.getExpenses())));
        tripStartDate.setText(trip.getStart_Date());
        tripEndDate.setText(trip.getEnd_Date());

        String type;
        if(trip.getType() != 1){
            type ="International";
        }else type="Domestic";

        tripType.setText(type);
    }
    private void confirmDeleteTripByID(){
        DatabaseHelper obj =new DatabaseHelper(getContext());
        Bundle bundleReceive = getArguments();
        Trip trip = (Trip) bundleReceive.get("object_trip");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Trip?");
        builder.setMessage("Do you want to delete trip to " +trip.getDestination()+ " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                mMainactivity = (MainActivity) getActivity();
                obj.deleteTripById(trip.getId());
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