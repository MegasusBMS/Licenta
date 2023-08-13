package app.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalFormatter {

    String s;

    public DecimalFormatter(String s){
        this.s=s;
    }

    public String format(double d){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat procentFormat = new DecimalFormat(s,symbols);
        return procentFormat.format(d);
    }

}
