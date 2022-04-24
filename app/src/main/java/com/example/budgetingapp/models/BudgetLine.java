package com.example.budgetingapp.models;

import com.example.budgetingapp.enums.CategoryType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class BudgetLine {
    private int id;
    private String category;
    private String description;
    private long date;
    private BigDecimal amount;
   public BudgetLine(int id, long date, String category, String description, double amount) {
    this.id = id;
    this.category = category;
    this.description = description;
    this.date = date;
    this.amount = new BigDecimal(amount);
   }
    BudgetLine (long date, String category, String description, String amount) {
        this.category = category;
        this.description = description;
        this.date = date;
        this.amount = new BigDecimal(amount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    // Use this for RecyclerView
    public String getTableFormatDate() {
       Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
       utc.setTimeInMillis(date);
       SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
       format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(utc.getTime());
    }
    
    // Use this for Edit Text to mimic Material Header Text
    public String getDatePickerFormatDate() {
        Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utc.setTimeInMillis(date);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(utc.getTime());
    }
    public void setDate(long date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = new BigDecimal(amount);
    }

    @Override
    public String toString() {
        return "BudgetLine{" +
                "id=" + id +
                ", category = " + category + '\'' +
                ", description = " + description + '\'' +
                ", date = " + getTableFormatDate() +
                ", amount = " + amount +
                '}';
    }

    public String getCategoryType() {
        CategoryType e = CategoryType.findByCategory(category);
        return e.getType();
    }
}
