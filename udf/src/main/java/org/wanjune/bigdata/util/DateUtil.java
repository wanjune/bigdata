package org.wanjune.bigdata.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * DateUtil
 *
 * @author wanjune
 * @date 2019/08/07
 */
public class DateUtil {

    /**
     * Calendar
     */
    private static final Calendar calendar = Calendar.getInstance();


    /**
     * Common Date-Format-Pattern
     */
    private static final List<String> DATE_PATTERN_LIST = Arrays.asList("yyyy-MM-dd HH:mm:ss zzz", "yyyy-MM-dd HH:mm:ss EE", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyyMMddHHmmss", "yyyyMMdd", "yyyy年MM月dd日", "yyyy/MM/dd");

    /**
     * the Date String Convert to Date
     *
     * @param dateString Date String
     * @return Date
     */
    public static Date toDate(String dateString) {
        SimpleDateFormat formatter;
        Date rDate = null;
        for (String datePatternString : DATE_PATTERN_LIST) {
            formatter = new SimpleDateFormat(datePatternString);
            try {
                rDate = formatter.parse(dateString);
                break;
            } catch (ParseException ex) {
                //continue;
            }
        }
        return rDate;
    }

    public static String quarter(Date pDate, String quarterPatternString) {
        calendar.setTime(pDate);

        int intYear = calendar.get(Calendar.YEAR);
        int intMonth = calendar.get(Calendar.MONTH) + 1;

        int intQuarter = 0;
        if (intMonth < 4) {
            intQuarter = 1;
        } else if (intMonth < 7) {
            intQuarter = 2;
        } else if (intMonth < 10) {
            intQuarter = 3;
        } else {
            intQuarter = 4;
        }

        return quarterPatternString.replace("yyyy", String.valueOf(intYear)).replace("QR", "Q".concat(String.valueOf(intQuarter))).replace("qr", String.valueOf(intQuarter));

    }

}
