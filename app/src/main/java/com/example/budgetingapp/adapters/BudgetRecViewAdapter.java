package com.example.budgetingapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetingapp.InputFieldsFragmentDirections;
import com.example.budgetingapp.R;
import com.example.budgetingapp.ViewAllFragmentDirections;
import com.example.budgetingapp.helpers.CurrencyHelper;
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
        int rowPos = holder.getAdapterPosition();
        if (rowPos == 0) {
            holder.txtDate.setBackgroundResource(R.drawable.bg_table_header);
            holder.txtDescription.setBackgroundResource(R.drawable.bg_table_header);
            holder.txtAmount.setBackgroundResource(R.drawable.bg_table_header);
            holder.txtDate.setTextColor(holder.view.getResources().getColor(R.color.white));
            holder.txtDescription.setTextColor(holder.view.getResources().getColor(R.color.white));
            holder.txtAmount.setTextColor(holder.view.getResources().getColor(R.color.white));
        } else {

            BudgetLine budgetItem = budgetItems.get(position-1);
            holder.txtDate.setText(budgetItem.getTableFormatDate());
            holder.txtDescription.setText(budgetItem.getDescription());
            holder.txtAmount.setText(CurrencyHelper.convertToFormatted(budgetItem.getAmount()));
            holder.parent.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ViewAllFragmentDirections.ActionEditTransaction action = ViewAllFragmentDirections.actionEditTransaction(budgetItem.getId());
                    Navigation.findNavController(v).navigate(action);
                }
            });

        }
    }
    public int getItemCount() {
        return budgetItems.size() + 1; // +1 to add a header

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private TextView txtDate;
        private TextView txtDescription;
        private TextView txtAmount;
        private LinearLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            parent = itemView.findViewById(R.id.parentLayout);
        }
    }

    public void setBudgetItems(ArrayList<BudgetLine> budgetItems) {
        this.budgetItems = budgetItems;
        notifyDataSetChanged();
    }
}
