package org.wanjune.bigdata.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
     * Date-Format(Pattern)
     * <p>
     * "yyyy-MM-dd HH:mm:ss zzz"<br/>
     * "yyyy-MM-dd HH:mm:ss EE"<br/>
     * "yyyy-MM-dd HH:mm:ss"<br/>
     * "yyyy-MM-dd HH:mm"<br/>
     * "yyyy-MM-dd"<br/>
     * "yyyyMMddHHmmss"<br/>
     * "yyyyMMdd"<br/>
     * "yyyy年MM月dd日"<br/>
     * "yyyy/MM/dd"
     */
    private static final List<String> DATE_PATTERN_LIST =
        Arrays.asList("yyyy-MM-dd HH:mm:ss zzz", "yyyy-MM-dd HH:mm:ss EE", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd", "yyyyMMddHHmmss", "yyyyMMdd", "yyyy年MM月dd日", "yyyy/MM/dd");

    /**
     * the Date String Convert to Date
     *
     * @param dateString
     *            Date String
     * @return Date
     */
    public static Date toDate(String dateString) {
        return toDate(dateString, null);
    }

    /**
     * the Date String Convert to Date
     *
     * @param dateString
     *            Date String
     * @param datePatternString
     *            special date-format[pattern]
     * @return Date
     */
    public static Date toDate(String dateString, String datePatternString) {
        SimpleDateFormat formatter;
        Date rDate = null;
        if (StringUtils.isEmpty(datePatternString)) {
            for (String datePatternItem : DATE_PATTERN_LIST) {
                formatter = new SimpleDateFormat(datePatternItem);
                try {
                    rDate = formatter.parse(dateString);
                    break;
                } catch (ParseException ex) {
                    // NOTHING
                }
            }
        } else {
            formatter = new SimpleDateFormat(datePatternString);
            try {
                rDate = formatter.parse(dateString);
            } catch (ParseException ex) {
                // NOTHING
            }
        }

        return rDate;
    }

    public static String quarter(Date pDate, String quarterPatternString) {
        calendar.setTime(pDate);

        String strYear = String.valueOf(calendar.get(Calendar.YEAR));
        int intMonth = calendar.get(Calendar.MONTH) + 1;

        String strQuarter = StringUtils.EMPTY;
        if (intMonth < 4) {
            strQuarter = "1";
        } else if (intMonth < 7) {
            strQuarter = "2";
        } else if (intMonth < 10) {
            strQuarter = "3";
        } else {
            strQuarter = "4";
        }

        return quarterPatternString.replace("yyyy", strYear).replace("QR", "Q".concat(strQuarter)).replace("qr",
            strQuarter);
    }

}
