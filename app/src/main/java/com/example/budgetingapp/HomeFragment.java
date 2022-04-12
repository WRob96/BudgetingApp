package com.example.budgetingapp;

import android.content.Context;
import android.icu.math.MathContext;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetingapp.models.BudgetLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Context thisContext;
    RecyclerView budgetRecView;
    ArrayList<BudgetLine> allTransactions;
    BigDecimal income;
    BigDecimal bills;
    BigDecimal debt;
    BigDecimal savings;
    BigDecimal investments;
    BigDecimal other;
    TextView incomeView;
    TextView billsView;
    TextView debtView;
    TextView savingsView;
    TextView investmentsView;
    TextView otherView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    void fetchHomeData() {
        allTransactions = ((MainActivity)getActivity()).db.readAllData();
        for (BudgetLine l : allTransactions) {
            switch(l.getCategoryType()) {
                case "Income":
                    income = income.add(l.getAmount());
                    break;
                case "Bills":
                    bills = bills.add(l.getAmount());
                    break;
                case "Debt":
                    debt = debt.add(l.getAmount());
                    break;
                case "Savings":
                    savings = savings.add(l.getAmount());
                    break;
                case "Investments":
                    investments = investments.add(l.getAmount());
                    break;
                case "Other":
                    other = other.add(l.getAmount());
                    break;
            }
            income = income.setScale(2, RoundingMode.HALF_UP);
            bills = bills.setScale(2, RoundingMode.HALF_UP);
            debt = debt.setScale(2, RoundingMode.HALF_UP);
            savings = savings.setScale(2, RoundingMode.HALF_UP);
            investments = investments.setScale(2, RoundingMode.HALF_UP);
            other = other.setScale(2, RoundingMode.HALF_UP);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Define view and context
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        thisContext = container.getContext();
        income = new BigDecimal(0);
        bills = new BigDecimal(0);
        debt = new BigDecimal(0);
        savings = new BigDecimal(0);
        investments = new BigDecimal(0);
        other = new BigDecimal(0);
        fetchHomeData();
        incomeView = (TextView)view.findViewById(R.id.incomeAmount);
        debtView = (TextView)view.findViewById(R.id.debtAmount);
        billsView = (TextView)view.findViewById(R.id.billsAmount);
        savingsView = (TextView)view.findViewById(R.id.savingsAmount);
        investmentsView = (TextView)view.findViewById(R.id.investmentsAmount);
        otherView = (TextView)view.findViewById(R.id.otherAmount);
        incomeView.setText(MessageFormat.format("${0}",income.toString()));
        debtView.setText(MessageFormat.format("${0}",debt.toString()));
        billsView.setText(MessageFormat.format("${0}",bills.toString()));
        savingsView.setText(MessageFormat.format("${0}",savings.toString()));
        investmentsView.setText(MessageFormat.format("${0}",investments.toString()));
        otherView.setText(MessageFormat.format("${0}",other.toString()));
        return view;

    }
}