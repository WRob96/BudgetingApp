package com.example.budgetingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetingapp.R;
import com.example.budgetingapp.models.BudgetLine;

import java.util.ArrayList;

/**
 * This class is a View Adapter for displaying budget items.
 */
public class BudgetViewAdapter extends RecyclerView.Adapter<BudgetViewAdapter.ViewHolder> {
    ArrayList<BudgetLine> budgetItems = new ArrayList<>();
    public BudgetViewAdapter() {

    }

    /**
     * This method is responsible for instantiating the view holder inner class.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_line_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BudgetLine budgetItem = budgetItems.get(position);
        holder.txtDate.setText(budgetItem.getDate().toString());
        holder.txtCategory.setText(budgetItem.getCategory());
        holder.txtDescription.setText(budgetItem.getDescription());
        holder.txtAmount.setText(budgetItem.getAmount().toString());
    }
    public int getItemCount() {
        return budgetItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtDate;
        private TextView txtCategory;
        private TextView txtDescription;
        private TextView txtAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }

    public void setBudgetItems(ArrayList<BudgetLine> budgetItems) {
        this.budgetItems = budgetItems;
        notifyDataSetChanged();
    }
}
