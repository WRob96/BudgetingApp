package com.example.budgetingapp.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;

public class MonthlyBudget {
    private ArrayList<BudgetLine> budgetLines;
    private YearMonth month;
    public MonthlyBudget(YearMonth month) {
        this.month = month;
    }
    public BigDecimal monthlyTotal() {
        BigDecimal total = new BigDecimal(0);
        for(BudgetLine l : budgetLines) {
            total = total.add(l.getAmount());
        }
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total;
    }
}
