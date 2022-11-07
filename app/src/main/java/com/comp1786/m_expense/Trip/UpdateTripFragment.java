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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Trip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateTripFragment extends Fragment {

    private View mView;

    private MainActivity mMainActivity;

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

//        EditText tripName = (EditText) view.findViewById(R.id.tripName);
//        EditText tripDestination = (EditText) view.findViewById(R.id.tripDestination);
//        EditText tripStartDate = (EditText) view.findViewById(R.id.tripStartDate);
//        EditText tripEndDate = (EditText) view.findViewById(R.id.tripEndDate);
//        EditText tripDescription = (EditText) view.findViewById(R.id.tripDescription);

        TextInputLayout tripName = (TextInputLayout) view.findViewById(R.id.tripName);
        TextInputLayout tripDestination = (TextInputLayout) view.findViewById(R.id.tripDestination);
        TextInputLayout tripStartDate = (TextInputLayout) view.findViewById(R.id.tripStartDate);
        TextInputLayout tripEndDate = (TextInputLayout) view.findViewById(R.id.tripEndDate);
        EditText tripDescription = (EditText) view.findViewById(R.id.tripDescription);

        RadioButton internal=view.findViewById(R.id.radio_internal);
        RadioButton external=view.findViewById(R.id.radio_external);
        RadioButton risk=view.findViewById(R.id.radio_yes);
        RadioButton noRisk=view.findViewById(R.id.radio_no);


        if( trip.getType()==1){
            internal.setChecked(true);
        }else {
            external.setChecked(true);
        }
        if(trip.getRisk()==1){
            risk.setChecked(true);
        }else {
            noRisk.setChecked(true);
        }


        mMainActivity = (MainActivity) getActivity();

        tripName.getEditText().setText(trip.getName());
        tripDestination.getEditText().setText(trip.getDestination());
        tripStartDate.getEditText().setText(trip.getStart_Date());
        tripEndDate.getEditText().setText(trip.getEnd_Date());
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
                            tripStartDate.getEditText().setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
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
                            tripEndDate.getEditText().setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.show ();
                }
            }
        });

        RadioGroup groupRisk =(RadioGroup) view.findViewById(R.id.groupRisk);
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


        RadioGroup groupType =(RadioGroup) view.findViewById(R.id.groupType);
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


        Button tripBtnUpdate = (Button) view.findViewById(R.id.tripBtnUpdate);
        tripBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper obj = new DatabaseHelper(getActivity());
                Trip Uptrip = new Trip();

                String TripName = tripName.getEditText().getText().toString().trim();
                String TripDes = tripDestination.getEditText().getText().toString().trim().toString();
                String TripStart = tripStartDate.getEditText().getText().toString().trim().toString();
                String TripEnd = tripEndDate.getEditText().getText().toString().trim().toString();
                if (TripName.isEmpty()) {
                    tripName.setError("Input Trip Name Please !!!");
                }else if (TripDes.isEmpty()){
                    tripDestination.setError("Input Destination please !!!");
                }else if (TripStart.isEmpty()){
                    tripStartDate.setError("Choose start date please !!!");
                }else if (TripEnd.isEmpty()){
                    tripEndDate.setError("Choose end date please !!!");
                }
                else {
                    Uptrip.setName(TripName);
                    Uptrip.setDestination(TripDes);
                    Uptrip.setStart_Date(TripStart);
                    Uptrip.setEnd_Date(TripEnd);
                    Uptrip.setDescription(tripDescription.getText().toString().trim().toString());
                    Uptrip.setRisk(risk_type);
                    Uptrip.setType(type_trip);

                    long result = obj.updateTrip(Uptrip, trip.getId());
                    if (result == -1) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update successfully!", Toast.LENGTH_SHORT).show();
                        mMainActivity.backToTripFragment();
                    }
                }
            }
        });
        return view;
    }
}