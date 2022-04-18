package com.example.budgetingapp;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetingapp.models.BudgetLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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

        getAllTransactions();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    private void getAllTransactions(){
        ListView transactionsListView;
        TextView infotext = (TextView) getActivity().findViewById(R.id.textView1);
        transactionsListView = (ListView) getActivity().findViewById(R.id.transactionsListView);
        //ArrayList<BudgetLine> transactionsData = ((MainActivity)getActivity()).db.readAllData();
        List<HashMap<String, String>> transactionsData = ((MainActivity)getActivity()).db.printAllTransactionsToString();
        if (transactionsData.size() !=0 ){

            String[] databaseTextFields = new String[]{"date", "description", "amount"};
            int[] viewTextFields = new int[]{R.id.textViewDATE, R.id.textViewDESCRIPTION, R.id.textViewDOLLAR_AMOUNT};
            SimpleAdapter myCursorAdapter = new SimpleAdapter(getContext(), transactionsData,
                    R.layout.listviewfields, databaseTextFields, viewTextFields);
            transactionsListView.setAdapter(myCursorAdapter);

        }
        else {infotext.setText("There is no Data Here!");}


    }
}