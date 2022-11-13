package com.comp1786.m_expense.Expense;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.comp1786.m_expense.DatabaseHelper;
import com.comp1786.m_expense.MainActivity;
import com.comp1786.m_expense.R;
import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Trip;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailExpenseFragment extends Fragment {
    private View mView;
    private MainActivity mMainactivity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailExpenseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailExpenseFragment newInstance(String param1, String param2) {
        DetailExpenseFragment fragment = new DetailExpenseFragment();
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
        Expenses expenses = (Expenses) bundleReceive.get("object_expense");
        mView = inflater.inflate(R.layout.fragment_detail_expense, container, false);
        mMainactivity = (MainActivity) getActivity();

        TextView exName = (TextView) mView.findViewById(R.id.exD_Name);
        TextView exType = (TextView) mView.findViewById(R.id.exD_type);
        TextView exAmount = (TextView) mView.findViewById(R.id.exD_Amount);
        TextView exAddress = (TextView) mView.findViewById(R.id.exD_address);
        TextView exDate = (TextView) mView.findViewById(R.id.exD_date);
        TextView exTime = (TextView) mView.findViewById(R.id.exD_time);
        ImageView exImage = (ImageView) mView.findViewById(R.id.exD_image);
        Button exportBtn = (Button) mView.findViewById(R.id.exD_export_btn);

        exName.setText(expenses.getName());
        exType.setText("chua nhan duoc");
        exAmount.setText((expenses.getAmount()).toString());
        exAddress.setText(expenses.getLocation());
        exDate.setText(expenses.getDate());
        exTime.setText(expenses.getTime());

        ImageButton editExpense = (ImageButton) mView.findViewById(R.id.exD_Edit);
        editExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainactivity.gotoUpdateExpenseFragment(expenses);
            }
        });

        ImageButton deleteExpense = (ImageButton) mView.findViewById(R.id.exD_delete);
        deleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteExpenseByID();
            }
        });


        return mView;
    }


    public void confirmDeleteExpenseByID(){
        DatabaseHelper obj =new DatabaseHelper(getContext());
        Bundle bundleReceive = getArguments();
        Expenses expenses = (Expenses) bundleReceive.get("object_expense");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Expense?");
        builder.setMessage("Do you want to delete expense " +expenses.getName()+ " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                mMainactivity = (MainActivity) getActivity();
                obj.deleteExpensesById(expenses.getId());
                mMainactivity.backToExpenseFragment();
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