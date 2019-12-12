package pl.app.controllers.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {

    private static SimpleDateFormat stringDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat viewDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    public static String parseDate(String dateToParse) {

        Date date = null;
        try {
            date = stringDateFormat.parse(dateToParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return viewDateFormat.format(date);

    }


}
