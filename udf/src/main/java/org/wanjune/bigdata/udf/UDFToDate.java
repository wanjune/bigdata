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
        return this.evaluate(dateText, null);
    }

    public DateWritable evaluate(Text dateText, Text datePatternText) {
        Date rdate = toDtate(dateText, datePatternText);

        if (null == rdate) {
            return null;
        }

        this.result.set(new java.sql.Date(rdate.getTime()));
        return this.result;
    }

    private Date toDtate(Text dateText, Text datePatternText) {
        return (null == dateText || StringUtils.isEmpty(dateText.toString())) ? null
            : (null == datePatternText || StringUtils.isEmpty(datePatternText.toString())
                ? DateUtil.toDate(dateText.toString())
                : DateUtil.toDate(dateText.toString(), datePatternText.toString()));
    }

}
