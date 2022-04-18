package com.example.budgetingapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputFieldsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFieldsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InputFieldsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFieldsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputFieldsFragment newInstance(String param1, String param2) {
        InputFieldsFragment fragment = new InputFieldsFragment();
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
        // Define view and context
        View view = inflater.inflate(R.layout.fragment_input_fields, container, false);
        Context context = container.getContext();
        // Create an ArrayAdapter using the string array and a default spinner layout
        Spinner spinner = view.findViewById(R.id.spinner);
        TextView descriptionField = (TextView) view.findViewById(R.id.descriptionfield);
        TextView amountInput = (TextView) view.findViewById(R.id.amountinput);
        TextView dateInput = (TextView) view.findViewById(R.id.dateinputfield);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                R.array.categories,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        Button submitButton = view.findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descriptionValue = descriptionField.getText().toString();
                String amountValue = amountInput.getText().toString();
                String dateValue = dateInput.getText().toString();
                String categoryValue = spinner.getSelectedItem().toString();
                ((MainActivity)getActivity()).db.addLine(categoryValue,descriptionValue, dateValue,amountValue);
                Toast.makeText(context, "Values: "+descriptionValue+amountValue+dateValue+categoryValue, Toast.LENGTH_SHORT).show();
            }
        });
        // Return view
        return view;
    }
}