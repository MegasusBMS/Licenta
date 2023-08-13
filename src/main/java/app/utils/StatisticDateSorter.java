package app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import app.statistics.StatisticContainer;
import app.statistics.StatisticGrupResult;

public class StatisticDateSorter {
    public static void sortByDateString(StatisticContainer container) {
        container.getListaStatistici().sort(Comparator.comparing(element -> {
            String dateString = ((StatisticGrupResult)element).getGrupa();
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM-yy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM");
            try {
                Date date = inputFormat.parse(dateString);
                return outputFormat.format(date);
            } catch (ParseException e) {
                // Handle parsing exception
                e.printStackTrace();
                return "";
            }
        },Comparator.reverseOrder()));
    }
}
