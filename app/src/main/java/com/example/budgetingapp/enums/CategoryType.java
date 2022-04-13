package com.example.budgetingapp.enums;

public enum CategoryType {
    INCOME("Income", "Income"),
    SAVINGS("Savings", "Savings"),
    INVESTMENTS("Investments", "Investments"),
    HOUSING("Housing", "Bills"),
    FOOD("Food", "Bills"),
    MEDICAL("Medical", "Bills"),
    TRANSPORTATION("Transportation", "Bills"),
    LIFE_INSURANCE("Insurance", "Bills"),
    CLOTHING("Clothing", "Bills"),
    CHILD_CARE("Child Care", "Bills"),
    CREDIT_CARDS("Credit Cards", "Debt"),
    LOANS("Loans", "Debt"),
    PERSONAL("Personal", "Other"),
    RECREATION_AND_ENTERTAINMENT("Recreation and Entertainment", "Other"),
    CHURCH_AND_CHARITIES("Church and Charities", "Other"),
    GIFTS("Gifts", "Other"),
    MISCELLANEOUS("Miscellaneous", "Other")
   ;
    private final String category;
    private final String type;
    CategoryType(String category, String type){
        this.category = category;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public static CategoryType findByCategory(String category) {
        CategoryType result = null;
        for (CategoryType categoryType : values()){
            if (categoryType.getCategory().equalsIgnoreCase(category)){
                result = categoryType;
                break;
            }
        }
        return result;
    }
}
