package com.example.budgetingapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetingapp.R;
import com.example.budgetingapp.models.BudgetLine;

import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * This class is a View Adapter for displaying budget items.
 */
public class BudgetRecViewAdapter extends RecyclerView.Adapter<BudgetRecViewAdapter.ViewHolder> {
    private ArrayList<BudgetLine> budgetItems = new ArrayList<>();
    public BudgetRecViewAdapter() {

    }

    /**
     * This method is responsible for instantiating the view holder inner class.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BudgetLine budgetItem = budgetItems.get(position);
        Log.d("debug", budgetItem.toString());
        holder.txtDate.setText(budgetItem.getDate().toString());
        holder.txtDescription.setText(budgetItem.getDescription());
        holder.txtAmount.setText(MessageFormat.format("${0}", budgetItem.getAmount().setScale(2, RoundingMode.HALF_UP).toString()));
    }
    public int getItemCount() {
        return budgetItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtDate;
        private TextView txtDescription;
        private TextView txtAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }

    public void setBudgetItems(ArrayList<BudgetLine> budgetItems) {
        this.budgetItems = budgetItems;
        notifyDataSetChanged();
    }
}
