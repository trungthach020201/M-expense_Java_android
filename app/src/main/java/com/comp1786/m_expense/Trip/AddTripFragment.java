package com.comp1786.m_expense.Trip;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
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
 * Use the {@link AddTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTripFragment extends Fragment {

    private int type_trip, risk_type;
    private int mYear, mMonth, mDay;
    private   String TripName, TripDes, TripStart, TripEnd,TripDescrip, Risk, Type;
    private TextInputLayout tripName, tripDestination, tripStartDate, tripEndDate;
    private TextInputEditText date_picker_action_start;
    private  EditText tripDescription;
    private Button tripBtnAdd;
    private RadioGroup groupType, groupRisk;
    private View view;

    public AddTripFragment() {
        // Required empty public constructor
    }

    public static AddTripFragment newInstance(String param1, String param2) {
        AddTripFragment fragment = new AddTripFragment();
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
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_trip, container,false);
        findObject();
        setTripStartDate();
        setTripEndDate();
        setTripRisk();
        setTripType();
        tripBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationInput();
            }
        });
        return view;
    }

    public void findObject(){
        tripName = (TextInputLayout) view.findViewById(R.id.tripName);
        tripDestination = (TextInputLayout) view.findViewById(R.id.tripDestination);
        tripStartDate = (TextInputLayout) view.findViewById(R.id.tripStartDate);
        tripEndDate = (TextInputLayout) view.findViewById(R.id.tripEndDate);
        date_picker_action_start = (TextInputEditText) view.findViewById(R.id.date_picker_action_start);
        tripDescription = (EditText) view.findViewById(R.id.tripDescription);
        tripBtnAdd = (Button) view.findViewById(R.id.tripBtnAdd);
        groupType =(RadioGroup) view.findViewById(R.id.groupType);
        groupRisk =(RadioGroup) view.findViewById(R.id.groupRisk);
    }

    public void setTripType(){
        groupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radio_internal:
                        risk_type=1;
                        Type ="International";
                        break;
                    case R.id.radio_external:
                        risk_type=0;
                        Type ="Domestic";
                        break;
                }
            }
        });
    }

    public void setTripStartDate(){
        date_picker_action_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == date_picker_action_start) {
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
    }

    public void setTripEndDate(){
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
    }

    public void setTripRisk(){
        groupRisk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radio_yes:
                        type_trip=1;
                        Risk= "yes";
                        break;
                    case R.id.radio_no:
                        type_trip=0;
                        Risk= "no";
                        break;
                }
            }
        });
    }
    public void getValueTrip(){
        TripName = tripName.getEditText().getText().toString().trim();
        TripDes = tripDestination.getEditText().getText().toString().trim().toString();
        TripStart = tripStartDate.getEditText().getText().toString().trim().toString();
        TripEnd = tripEndDate.getEditText().getText().toString().trim().toString();
        TripDescrip = tripDescription.getText().toString().trim().toString();
    }
    public void validationInput(){
        getValueTrip();
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
            confirmAdd();
        }
    }

    public void confirmAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Information");
        builder.setMessage("Add new trip with follow information: "+
                "\nTrip name: " + TripName +
                "\nTrip destination: " + TripDes +
                "\nStart date: " + TripStart +
                "\nEnd date: " + TripEnd +
                "\nTrip type: " +Type+
                "\nTrip risk: " +Risk +
                "\nDescription: " +TripDescrip );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                addNewTrip();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
            }
        });
        builder.create().show();
    }

    public void addNewTrip(){
        DatabaseHelper obj = new DatabaseHelper(getActivity());
        Trip trip = new Trip();
        MainActivity mMainActivity = (MainActivity) getActivity();
        trip.setName(TripName);
        trip.setDestination(TripDes);
        trip.setStart_Date(TripStart);
        trip.setEnd_Date(TripEnd);
        trip.setDescription(TripDescrip);
        trip.setRisk(risk_type);
        trip.setType(type_trip);
        long result = obj.addTrip(trip);
        if (result == -1) {
            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Add successfully!", Toast.LENGTH_SHORT).show();
            mMainActivity.backToTripFragment();
        }
    }
}