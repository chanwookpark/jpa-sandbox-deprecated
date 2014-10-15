package io.noah.jpasandbox.framework.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by chanwook on 2014. 10. 13..
 */
public class DateUtils {
    public static Date getDate(int year, int month, int date) {
        Calendar c = GregorianCalendar.getInstance();
        c.set(year, month, date);
        return c.getTime();
    }
}
