package com.example.budgetingapp.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;

public class CurrencyHelper {
    /**
     * Method converts BigDecimal to formatted USD String
     */
    public static String convertToFormatted(BigDecimal amount) {
        DecimalFormatSymbols custom=new DecimalFormatSymbols();
        custom.setDecimalSeparator('.');
        custom.setGroupingSeparator(',');

        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        format.setDecimalFormatSymbols(custom);
        format.setCurrency(Currency.getInstance("USD"));
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(amount);

    }
    public static BigDecimal convertFromFormatted(String amount) throws ParseException {
        DecimalFormatSymbols custom=new DecimalFormatSymbols();
        custom.setDecimalSeparator('.');
        custom.setGroupingSeparator(',');

        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        format.setDecimalFormatSymbols(custom);
        format.setCurrency(Currency.getInstance("USD"));
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return new BigDecimal((format.parse(amount)).toString());
    }
}
