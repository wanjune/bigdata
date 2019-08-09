package org.wanjune.bigdata.udf;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.io.Text;
import org.wanjune.bigdata.util.DateUtil;

/**
 * UDFToDate
 *
 * @author wanjune
 * @date 2019/08/07
 */
@Description(name = "toDate", value = "_FUNC_(dateStr[, pattern]) - Returns the Date of String ",
    extended = "Converts the specified date/time(String) to Date.\n"
        + "Example:\n   > SELECT _FUNC_('2019/12/31') FROM src LIMIT 1;\n  2019-12-31\n"
        + "Example:\n   > SELECT _FUNC_('2019年06月30日', 'yyyy年MM月dd日') FROM src LIMIT 1;\n  2019-06-30\n")
public class UDFToDate extends UDF {
    private final DateWritable result = new DateWritable();

    public UDFToDate() {}

    public DateWritable evaluate(Text dateText) {

        if (null == dateText || StringUtils.isEmpty(dateText.toString())) {
            return null;
        }

        Date rdate = DateUtil.toDate(dateText.toString());

        if (null != rdate) {
            this.result.set(new java.sql.Date(rdate.getTime()));
            return this.result;
        } else {
            return null;
        }

    }

    public DateWritable evaluate(Text dateText, Text datePatternText) {

        if (null == dateText || StringUtils.isEmpty(dateText.toString())) {
            return null;
        }

        Date rdate = null;
        if (null != datePatternText && !StringUtils.isEmpty(datePatternText.toString())) {
            rdate = DateUtil.toDate(dateText.toString(), datePatternText.toString());
        } else {
            rdate = DateUtil.toDate(dateText.toString());
        }

        if (null != rdate) {
            this.result.set(new java.sql.Date(rdate.getTime()));
            return this.result;
        } else {
            return null;
        }
    }

}
