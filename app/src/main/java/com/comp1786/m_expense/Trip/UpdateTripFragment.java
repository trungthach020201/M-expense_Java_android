package com.comp1786.m_expense.Trip;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Trip;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateTripFragment extends Fragment {

    private View mView;
    private RecyclerView rcvDetailTrip;
    private MainActivity mMainactivity;

    int type_trip, risk_type;
    private int mYear, mMonth, mDay;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateTripFragment newInstance(String param1, String param2) {
        UpdateTripFragment fragment = new UpdateTripFragment();
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

        Bundle bundleReceive = getArguments();
        Trip trip = (Trip) bundleReceive.get("object_trip");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_update_trip, container,false);

        EditText tripName = (EditText) view.findViewById(R.id.tripName);
        EditText tripDestination = (EditText) view.findViewById(R.id.tripDestination);
        EditText tripStartDate = (EditText) view.findViewById(R.id.tripStartDate);
        EditText tripEndDate = (EditText) view.findViewById(R.id.tripEndDate);
        EditText tripDescription = (EditText) view.findViewById(R.id.tripDescription);

        Button tripBtnUpdate = (Button) view.findViewById(R.id.tripBtnUpdate);

        RadioGroup groupType =(RadioGroup) view.findViewById(R.id.groupType);
        RadioGroup groupRisk =(RadioGroup) view.findViewById(R.id.groupRisk);

        tripName.setText(trip.getName());
        tripDestination.setText(trip.getDestination());
        tripStartDate.setText(trip.getStart_Date());
        tripEndDate.setText(trip.getEnd_Date());
        tripDescription.setText(trip.getDescription());



        tripStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == tripStartDate) {
                    final Calendar calendar = Calendar.getInstance ();
                    mYear = calendar.get ( Calendar.YEAR );
                    mMonth = calendar.get ( Calendar.MONTH );
                    mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                    //show dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            tripStartDate.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.show ();
                }
            }
        });


        tripEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == tripEndDate) {
                    final Calendar calendar = Calendar.getInstance ();
                    mYear = calendar.get ( Calendar.YEAR );
                    mMonth = calendar.get ( Calendar.MONTH );
                    mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                    //show dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            tripEndDate.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.show ();
                }
            }
        });


        groupRisk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radio_internal:
                        risk_type=1;
                        break;
                    case R.id.radio_external:
                        risk_type=0;
                        break;
                }
            }
        });


        groupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radio_yes:
                        type_trip=1;
                        break;
                    case R.id.radio_no:
                        type_trip=0;
                        break;
                }
            }
        });



        tripBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper obj = new DatabaseHelper(getActivity());
                Trip trip = new Trip();

                trip.setName(tripName.getText().toString().trim());
                trip.setDestination(tripDestination.getText().toString().trim().toString());
                trip.setStart_Date(tripStartDate.getText().toString().trim().toString());
                trip.setEnd_Date(tripEndDate.getText().toString().trim().toString());
                trip.setDescription(tripDescription.getText().toString().trim().toString());
                trip.setRisk(risk_type);
                trip.setType(type_trip);
                obj.updateTrip(trip,trip.getId());

            }
        });


        return view;
    }
}