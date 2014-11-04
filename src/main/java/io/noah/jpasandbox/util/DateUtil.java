package io.noah.jpasandbox.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chanwook on 2014. 11. 4..
 */
public class DateUtil {

    public static Date toDate(String source) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
