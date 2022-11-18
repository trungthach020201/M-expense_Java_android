package com.comp1786.m_expense.Expense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

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
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Type;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateExpenseFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private MainActivity mMainActivity;
    TextInputLayout upExDate;
    TextInputLayout upExTime;
    TextInputLayout upExAddress;
    TextInputLayout upExName;
    TextInputLayout upExAmount;
    TextInputLayout url;
    Spinner upExType;
    Button cancleBtn;
    EditText upExComment;
    private int mYear,mMonth,mDay,mHour,mMinute;
    private List<String> typesName;
    private View view;
    ImageView imageView;
    TextInputLayout exOtherType;
    int Type_Id;
    String typeExpense;
    int typeExpenseId;
    Button btnLoad;

    public UpdateExpenseFragment() {
        // Required empty public constructor
    }

    public static UpdateExpenseFragment newInstance(String param1, String param2) {
        UpdateExpenseFragment fragment = new UpdateExpenseFragment();
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
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_update_expense, container, false);
        loadObject();
        return view;
    }
    public void loadImage(String image_url){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher);
        Glide.with((UpdateExpenseFragment)this).load(image_url).apply(options).into(imageView);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if(position==0){
            Type_Id=typeExpenseId;
        }else {
            Type_Id=position+1;
        }
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        exOtherType.setEnabled(true);
    }

    public void loadObject(){
        Bundle bundleReceive = getArguments();
        Expenses expense = (Expenses) bundleReceive.get("object_expense");
        findObject();
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.onBackPressed();
            }
        });

        updateDate();
        updateTime();

        mMainActivity = (MainActivity) getActivity();
        upExName.getEditText().setText(expense.getName());
        System.out.println(expense.getDate());
        upExDate.getEditText().setText(expense.getDate().toString());
        upExTime.getEditText().setText(expense.getTime().toString());
        upExAddress.getEditText().setText(expense.getLocation());
        upExAmount.getEditText().setText((expense.getAmount()).toString());
        upExComment.setText(expense.getComment());
        url.getEditText().setText(expense.getImage());
        loadImage(expense.getImage());
        DatabaseHelper ob=new DatabaseHelper(getActivity());
        DatabaseHelper obj = new DatabaseHelper(getActivity());
        Type typeObj=obj.searchTypeById(expense.getType_id());
        typeExpense=typeObj.getName();
        List<Type> types = obj.getListType();
        typeExpenseId=typeObj.getId();
        typesName=new ArrayList<>();
        typesName.add(typeExpense);

        for (Type type: types) {
            if(!type.getName().equals(typeExpense)){
                typesName.add(type.getName());
            }
        }

        upExType.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typesName);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        upExType.setAdapter(dataAdapter);

        Button updateBtn = (Button) view.findViewById(R.id.btnUpEx);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenses Upexpense = new Expenses();
                String ExName = upExName.getEditText().getText().toString().trim();
                String ExDate = upExDate.getEditText().getText().toString().trim();
                String ExLocation = upExAddress.getEditText().getText().toString().trim();
                String ExTime = upExTime.getEditText().getText().toString().trim();
                String ExAmount = upExAmount.getEditText().getText().toString().trim();
                String ExURL = url.getEditText().getText().toString().trim();
                String OtherType = exOtherType.getEditText().getText().toString().trim();
                if (ExName.isEmpty()) {
                    upExName.setError("Enter Expense Name please!!!");
                } else if (ExDate.isEmpty()) {
                    upExDate.setError("Choose Date please!!!");
                } else if (ExLocation.isEmpty()) {
                    upExAddress.setError("Enter please!!!");
                } else if (ExTime.isEmpty()) {
                    upExTime.setError("Choose Time please!!!");
                } else if (ExAmount.isEmpty()) {
                    upExAmount.setError("Enter amount expense please!!!");
                } else {
                    Upexpense.setName(ExName);
                    Upexpense.setDate(ExDate);
                    Upexpense.setTime(ExTime);
                    Upexpense.setLocation(ExLocation);
                    Upexpense.setComment(upExComment.getText().toString().trim());
                    Upexpense.setAmount(Float.valueOf(ExAmount));
                    if (!OtherType.isEmpty()) {
                        ob.addType(new Type(1, exOtherType.getEditText().getText().toString()));
                        Type_Id = types.size() + 1;
                    }
                    Upexpense.setTrip_id(expense.getTrip_id());
                    Upexpense.setType_id(Type_Id);
                    btnLoad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadImage(ExURL);
                        }
                    });
                    Upexpense.setImage(ExURL);

                    long result = obj.updateExpenses(Upexpense, expense.getId());
                    if (result == -1) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update successfully!", Toast.LENGTH_SHORT).show();
                        mMainActivity.onBackPressed();
                    }
                }
            }
        });
    }
    public void updateDate(){
        upExDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == upExDate) {
                    final Calendar calendar = Calendar.getInstance ();
                    mYear = calendar.get ( Calendar.YEAR );
                    mMonth = calendar.get ( Calendar.MONTH );
                    mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                    //show dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            upExDate.getEditText().setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.show ();
                }
            }
        });
    }
    public void updateTime(){
        upExTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == upExTime) {

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

                                    upExTime.getEditText().setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });

    }
    private void findObject(){
        upExName = (TextInputLayout) view.findViewById(R.id.exup_name_txt);
        upExDate = (TextInputLayout) view.findViewById(R.id.exup_date_txt);
        upExTime = (TextInputLayout) view.findViewById(R.id.exup_time_txt);
        upExAddress = (TextInputLayout) view.findViewById(R.id.exup_adress_txt);
        upExAmount = (TextInputLayout) view.findViewById(R.id.exup_amount_txt);
        upExComment = (EditText) view.findViewById(R.id.exup_comment_txt);
        upExType = (Spinner) view.findViewById(R.id.UpdropdownType);
        url=(TextInputLayout) view.findViewById(R.id.ex_url);
        btnLoad=(Button) view.findViewById(R.id.btnLoad);
        exOtherType=(TextInputLayout) view.findViewById(R.id.exup_othertype);
        imageView = (ImageView) view.findViewById(R.id.exup_image);
        cancleBtn = (Button) view.findViewById(R.id.btnCancelExUp);
    }
}