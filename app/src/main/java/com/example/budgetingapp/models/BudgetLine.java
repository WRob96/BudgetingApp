package com.example.budgetingapp.models;

import com.example.budgetingapp.enums.CategoryType;

import java.math.BigDecimal;
import java.sql.Date;

public class BudgetLine {
    private int id;
    private String category;
    private String description;
    private Date date;
    private BigDecimal amount;
   public BudgetLine(int id, Date date, String category, String description, BigDecimal amount) {
    this.id = id;
    this.category = category;
    this.description = description;
    this.date = date;
    this.amount = amount;
   }
    BudgetLine (Date date, String category, String description, BigDecimal amount) {
        this.category = category;
        this.description = description;
        this.date = date;
        this.amount = amount;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BudgetLine{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }

    public String getCategoryType() {
        CategoryType e = CategoryType.findByCategory(category);
        return e.getType();
    }
}
