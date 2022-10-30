package com.comp1786.m_expense.Expense;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Trip;
import com.comp1786.m_expense.model.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateExpenseFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private MainActivity mMainActivity;
    private EditText exOtherType, url;
    private List<String> typesName;
    ImageView imageView;
    int Type_Id;
    Button btnLoad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateExpenseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateExpenseFragment newInstance(String param1, String param2) {
        UpdateExpenseFragment fragment = new UpdateExpenseFragment();
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
        Expenses expense = (Expenses) bundleReceive.get("object_expense");

        View view = inflater.inflate(R.layout.fragment_update_expense, container, false);

        EditText upExName = (EditText) view.findViewById(R.id.exup_name_txt);
        EditText upExDate = (EditText) view.findViewById(R.id.exup_date_txt);
        EditText upExTime = (EditText) view.findViewById(R.id.exup_time_txt);
        EditText upExAddress = (EditText) view.findViewById(R.id.exup_adress_txt);
        EditText upExAmount = (EditText) view.findViewById(R.id.exup_amount_txt);
        EditText upExComment = (EditText) view.findViewById(R.id.exup_comment_txt);
        Spinner upExType = (Spinner) view.findViewById(R.id.UpdropdownType);
        url=(EditText) view.findViewById(R.id.ex_url);
        btnLoad=(Button) view.findViewById(R.id.btnLoad);
        exOtherType=(EditText) view.findViewById(R.id.exup_othertype);
        imageView = (ImageView) view.findViewById(R.id.exup_image);
        Button cancleBtn = (Button) view.findViewById(R.id.btnCancelExUp);

         mMainActivity = (MainActivity) getActivity();

        upExName.setText(expense.getName());
        System.out.println(expense.getDate());
        upExDate.setText(expense.getDate().toString());
        upExTime.setText(expense.getTime().toString());
        upExAddress.setText(expense.getLocation());
        upExAmount.setText((expense.getAmount()).toString());
        upExComment.setText(expense.getComment());
        url.setText(expense.getImage());
        System.out.println(expense.getImage());
        loadImage(expense.getImage());

        DatabaseHelper obj = new DatabaseHelper(getActivity());

        List<Type> types = obj.getListType();
        typesName=new ArrayList<>();

        for (Type type: types) {
            typesName.add(type.getName());
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

                Upexpense.setName(upExName.getText().toString().trim());
                Upexpense.setDate(upExDate.getText().toString().trim());
                Upexpense.setTime(upExTime.getText().toString().trim());
                Upexpense.setLocation(upExAddress.getText().toString().trim());
                Upexpense.setComment(upExComment.getText().toString().trim());
                Upexpense.setAmount(Float.valueOf(upExAmount.getText().toString().trim()));
                if(!exOtherType.getText().toString().trim().isEmpty()){
                    obj.addType(new Type(1,exOtherType.getText().toString()));
                    Type_Id=types.size()+1;
                }
                Upexpense.setTrip_id(expense.getTrip_id());
                Upexpense.setType_id(Type_Id);
                btnLoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadImage(url.getText().toString());
                    }
                });
                Upexpense.setImage(url.getText().toString().trim());

                long result = obj.updateExpenses(Upexpense,expense.getId());
                if(result==-1){
                    Toast.makeText(getContext(),"Failed", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"Update successfully!", Toast.LENGTH_SHORT).show();
                    mMainActivity.backToTripFragment();
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
        Glide.with((UpdateExpenseFragment)this).load(image_url).apply(options).into(imageView);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        Type_Id=position+1;
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        exOtherType.setEnabled(true);
    }
}