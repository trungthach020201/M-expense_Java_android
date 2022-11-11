package com.comp1786.m_expense.Expense;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Trip;
import com.comp1786.m_expense.model.Type;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExpenseFragment extends Fragment implements AdapterView.OnItemSelectedListener  {

    private MainActivity mMainActivity;
    private int mYear,mMonth,mDay,mHour,mMinute;
    private int Type_Id=0;
    private EditText exOtherType, url;
    private List<String> typesName;
    ImageView imageView;
    Button btnLoad;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddExpenseFragment newInstance(String param1, String param2) {
        AddExpenseFragment fragment = new AddExpenseFragment();
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
        int tripId = (int) bundleReceive.get("trip_id");
        DatabaseHelper ob =new DatabaseHelper(getContext());
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        mMainActivity = (MainActivity) getActivity();
        TextInputLayout exName = (TextInputLayout) view.findViewById(R.id.ex_name_txt);
        TextInputLayout exAddress = (TextInputLayout) view.findViewById(R.id.ex_adress_txt);
        TextInputLayout exAmount = (TextInputLayout) view.findViewById(R.id.ex_amount_txt);
        EditText exComment = (EditText) view.findViewById(R.id.ex_comment_txt);
        TextInputLayout exOtherType=(TextInputLayout) view.findViewById(R.id.ex_other_txt);
        imageView = (ImageView) view.findViewById(R.id.ex_image);
        TextInputLayout url=(TextInputLayout) view.findViewById(R.id.ex_url);
        btnLoad=(Button) view.findViewById(R.id.btnLoad);

        TextInputLayout exDate = (TextInputLayout) view.findViewById(R.id.ex_date_txt);
        exDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == exDate) {
                    final Calendar calendar = Calendar.getInstance ();
                    mYear = calendar.get ( Calendar.YEAR );
                    mMonth = calendar.get ( Calendar.MONTH );
                    mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                    //show dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            exDate.getEditText().setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.show ();
                }
            }
        });

        TextInputLayout exTime = (TextInputLayout) view.findViewById(R.id.ex_time_txt);
        exTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == exTime) {

                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    exTime.getEditText().setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });

        btnLoad.setOnClickListener(v->{
            System.out.println(url.getEditText().getText().toString().trim());
            loadImage(url.getEditText().getText().toString().trim());
        });

        final Spinner exType = (Spinner) view.findViewById(R.id.dropdownType);

        // Spinner Drop down elements
        List<Type> types = ob.getListType();
        typesName=new ArrayList<>();

        for (Type type: types) {
            typesName.add(type.getName());
        }

        exType.setOnItemSelectedListener(this);
// Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typesName);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        exType.setAdapter(dataAdapter);


        Button exCancel = (Button) view.findViewById(R.id.btnCancelEx);
        exCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.onBackPressed();
            }
        });

        Button exAdd = (Button) view.findViewById(R.id.btnAddEx);
        exAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper obj = new DatabaseHelper(getActivity());
                Expenses expenses = new Expenses();

                String ExName = exName.getEditText().getText().toString().trim();
                String ExDate = exDate.getEditText().getText().toString().trim();
                String ExLocation = exAddress.getEditText().getText().toString().trim();
                String ExTime = exTime.getEditText().getText().toString().trim();
                String ExAmount = exAmount.getEditText().getText().toString().trim();
                String ExURL = url.getEditText().getText().toString().trim();
                String OtherType = exOtherType.getEditText().getText().toString().trim();

                if(ExName.isEmpty()){
                    exName.setError("Enter Expense Name please!!!");
                }else if(ExDate.isEmpty()){
                    exDate.setError("Choose Date please!!!");
                }else if (ExLocation.isEmpty()){
                    exAddress.setError("Enter please!!!");
                }else if(ExTime.isEmpty()){
                    exTime.setError("Choose Time please!!!");
                }else if (ExAmount.isEmpty()){
                    exAmount.setError("Enter amount expense please!!!");
                }
                else{
                    expenses.setName(ExName);
                    expenses.setDate(ExDate);
                    expenses.setLocation(ExLocation);
                    expenses.setTime(ExTime);
                    expenses.setAmount(Float.valueOf(ExAmount));
                    expenses.setComment(exComment.getText().toString().trim());
                    expenses.setImage(ExURL);
                    expenses.setTrip_id(tripId);
                    if (!OtherType.isEmpty()) {
                        obj.addType(new Type(1, exOtherType.getEditText().getText().toString()));
                        Type_Id = types.size() + 1;
                    }
                    expenses.setType_id(Type_Id);
                    long result = obj.addExpense(expenses);
                    if (result == -1) {
                        Toast.makeText(getContext(), "Add Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Add successfully!", Toast.LENGTH_SHORT).show();
                        mMainActivity.onBackPressed();
                    }
                }

            }
        });
        return view;

    }

    public void loadImage(String image_url){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher);

        Glide.with((AddExpenseFragment)this).load(image_url).apply(options).into(imageView);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        Type_Id=position+1;
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        exOtherType.setEnabled(true);
    }
}