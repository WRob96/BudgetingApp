package com.example.budgetingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputFieldsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFieldsFragment extends Fragment {

    View view;
    Context context;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Define view and context
        view = inflater.inflate(R.layout.fragment_input_fields, container, false);
        context = container.getContext();

        // Declaring Views
        TextInputLayout categoryInput = view.findViewById(R.id.categoryInputField);
        AutoCompleteTextView categoryChild = view.findViewById(R.id.categoryAutoComplete);
        TextInputLayout descriptionField = (TextInputLayout) view.findViewById(R.id.descriptionField);
        TextInputLayout amountInput = (TextInputLayout) view.findViewById(R.id.amountInput);
        TextInputLayout dateInput = (TextInputLayout) view.findViewById(R.id.dateInputField);
        //Initialize Dropdown for category fields
        String[] categoryArray = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                context,
                R.layout.dropdown_item,
                categoryArray
        );

        categoryChild.setAdapter(categoryAdapter);

        // Customizing Date Input behavior
        dateInput.getEditText().setInputType(InputType.TYPE_NULL);
        dateInput.getEditText().setKeyListener(null);
        // Material Date Picker build
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Date of Transaction")
                .build();
        final String datePickerTag = "TRANSACTION_DATE";
        // Setting Callbacks
        View.OnClickListener callback = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getChildFragmentManager(), datePickerTag);
                dateInput.setFocusedByDefault(true);
            }
        };
        View.OnFocusChangeListener callback2 = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    datePicker.show(getChildFragmentManager(), datePickerTag);
                }
            }
        };
        // Setting on click and focus change listeners
        dateInput.getEditText().setOnClickListener(callback);
        dateInput.getEditText().setOnFocusChangeListener(callback2);
        // Listen on date change
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                                                        @Override
                                                        public void onPositiveButtonClick(Long selection) {
                                                            dateInput.getEditText().setText(datePicker.getHeaderText());
                                                        }
                                                    });
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
        // Set onClickListener
        Button submitButton = view.findViewById(R.id.addNewButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = categoryChild.getText().toString();
                String description = descriptionField.getEditText().getText().toString();
                String amount = amountInputEdit.getText().toString();
                if (category.equals("") || description.equals("") || datePicker.getSelection() == null || amount.equals("") || amount.equals("$0.00")){
                    Snackbar.make(view, "Please fill out all fields", Snackbar.LENGTH_SHORT).show();
                } else {
                    try {
                      long result = ((MainActivity)getActivity()).db.addLine(category, description, datePicker.getSelection(), amount);
                      if (result != -1) {
                          Snackbar.make(view, "Transaction Successfully Added!", Snackbar.LENGTH_SHORT).show();
                          Navigation.findNavController(view).navigate(R.id.inputFieldsFragment);
                      } else {
                          Snackbar.make(view, "Something Went Wrong...", Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.red)).show();
                      }
                    } catch(ParseException e) {
                        Snackbar.make(view, "Parse Error!", Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.red)).show();
                        Log.d("error", e.toString());
                    }
                }
            }
        });
        // Return view
        return view;
    }

    /**
     * This function refreshes the dropdown menu on resuming of a fragment lifecycle
     */
   /* @Override
    public void onResume() {
        super.onResume();
        String[] categoryArray = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                context,
                R.layout.dropdown_item,
                categoryArray
        );
        AutoCompleteTextView categoryChild = view.findViewById(R.id.categoryAutoComplete);
        categoryChild.setAdapter(categoryAdapter);
    }*/
}