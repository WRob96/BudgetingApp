package com.example.budgetingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import android.widget.Toast;

import com.example.budgetingapp.helpers.CurrencyHelper;
import com.example.budgetingapp.models.BudgetLine;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTransactionFragment extends Fragment {
    Context thisContext;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int id;
    public EditTransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditTransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTransactionFragment newInstance(String param1, String param2) {
        EditTransactionFragment fragment = new EditTransactionFragment();
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

        View view = inflater.inflate(R.layout.fragment_edit_transaction, container, false);
        Context context = container.getContext();
        // Get the id passed in action with Safe Args
        id = EditTransactionFragmentArgs.fromBundle(getArguments()).getId();
        BudgetLine line = ((MainActivity)getActivity()).db.findById(id);
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
                .setSelection(line.getDate())
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
        // Initialize current values of fields in transaction


        categoryChild.setText(line.getCategory(), false); // Must pass false filter to show all values in drop down
        descriptionField.getEditText().setText(line.getDescription());
        dateInput.getEditText().setText(line.getDatePickerFormatDate()); // Uses custom method set in model for original field population
        amountInputEdit.setText(CurrencyHelper.convertToFormatted(line.getAmount())); // Uses custom Currency Helper to populate field from BigDecimal
        // Set onClickListeners for buttons
        Button updateButton = view.findViewById(R.id.updateButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = categoryChild.getText().toString();
                String description = descriptionField.getEditText().getText().toString();
                long date = datePicker.getSelection();
                String amount = amountInputEdit.getText().toString();
                if (category.equals("") || description.equals("") || amount.equals("") || amount.equals("$0.00")) {
                    Snackbar.make(view, "Please fill out all fields", Snackbar.LENGTH_SHORT).show();
                } else {
                    long result;
                    try {
                        result = ((MainActivity)getActivity()).db.updateRow(new BudgetLine(line.getId(), datePicker.getSelection(), category, description, (CurrencyHelper.convertFromFormatted(amount)).doubleValue())); // This is incredibly ugly, but I'm too tired to do better!
                    } catch (ParseException e) {
                        e.printStackTrace();
                        result = -1;
                    }
                    if (result == -1) {
                        Snackbar.make(view, "Something Went Wrong...", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(view, "Transaction Updated!", Snackbar.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.viewAllFragment);
                    }
                }
                }
        });
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
               builder.setMessage(R.string.are_you_sure);
               builder.setTitle(R.string.delete_question);
               builder.setNeutralButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        long result = ((MainActivity)getActivity()).db.deleteRow(line);
                        dialog.cancel();
                        if (result == -1) {
                            Snackbar.make(view, "Something Went Wrong...", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(view, "Transaction Deleted!", Snackbar.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.viewAllFragment);
                        }
                    }
                });
                builder.create().show();

            }
        });
        return view;
                }
}