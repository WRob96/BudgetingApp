package com.example.budgetingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;

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

    // Declaring transaction variables
    String category;
    String description;
    BigDecimal amount;
    Long date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Define view and context
        View view = inflater.inflate(R.layout.fragment_input_fields, container, false);
        Context context = container.getContext();

        // Declaring Views
        Spinner spinner = view.findViewById(R.id.spinner);
        TextView descriptionField = (TextView) view.findViewById(R.id.descriptionField);
        TextInputLayout amountInput = (TextInputLayout) view.findViewById(R.id.amountInput);
        TextInputLayout dateInput = (TextInputLayout) view.findViewById(R.id.dateInputField);
        // Customizing Date Input behavior
        dateInput.getEditText().setInputType(InputType.TYPE_NULL);
        dateInput.getEditText().setKeyListener(null);
        // Material Date Picker build
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Date of Transaction")
                .build();
        final String datePickerTag = "TRANSACTION_DATE";
        // Setting Callback
        View.OnClickListener callback = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getChildFragmentManager(), datePickerTag);
            }
        };
        // Setting On Click Listeners
        dateInput.getEditText().setOnClickListener(callback);
        // Listen on date change
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                                                        @Override
                                                        public void onPositiveButtonClick(Long selection) {
                                                            dateInput.getEditText().setText(datePicker.getHeaderText());
                                                            date = selection;
                                                        }
                                                    });
                ArrayAdapter < CharSequence > adapter = ArrayAdapter.createFromResource(
                        context,
                        R.array.categories,
                        android.R.layout.simple_spinner_item
                );
        // Set behavior of amount field
        EditText amountInputEdit = amountInput.getEditText();
       amountInputEdit.addTextChangedListener(new TextWatcher() {
            private String current;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(current)){
       amountInputEdit.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                    current = formatted;
       amountInputEdit.setText(formatted);
       amountInputEdit.setSelection(formatted.length());
       amountInputEdit.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set onClickListener
        Button submitButton = view.findViewById(R.id.addNewButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // Return view
        return view;
    }
}