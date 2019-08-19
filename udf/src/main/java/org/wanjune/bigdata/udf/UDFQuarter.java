package org.wanjune.bigdata.udf;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.wanjune.bigdata.util.DateUtil;

/**
 * UDFQuarter
 *
 * @author wanjune
 * @date 2019/08/07
 */
@Description(name = "quarter",
    value = "_FUNC_(date[, pattern]) - Returns the quarter of the year for a date/timestamp/string by default[yyyyQR]/specified ",
    extended = "Converts the specified time(date/timestamp/string) to string with default[yyyyQR]/specified format.\n"
        + "Example:\n   > SELECT _FUNC_('20190302') FROM src LIMIT 1;\n  2019Q1\n"
        + "Example:\n   > SELECT _FUNC_('20190302', 'yyyyQR') FROM src LIMIT 1;\n  2019Q1\n"
        + "Example:\n   > SELECT _FUNC_('20190302', 'yyyy-QR') FROM src LIMIT 1;\n  2019-Q1\n"
        + "Example:\n   > SELECT _FUNC_('20190302', 'yyyy-qr') FROM src LIMIT 1;\n  2019-1\n"
        + "Example:\n   > SELECT _FUNC_('20190302', 'QR') FROM src LIMIT 1;\n  Q1\n"
        + "Example:\n   > SELECT _FUNC_('20190302', 'qr') FROM src LIMIT 1;\n  1\n")
public class UDFQuarter extends UDF {

    private final static String DEFAULT_QUARTER_PATTERN = "yyyyQR";

    private final Text result = new Text();

    public UDFQuarter() {}

    public Text evaluate(NullWritable dateText) {
        return this.result;
    }

    public Text evaluate(NullWritable dateText, Text quarterPatternText) {
        return this.result;
    }

    public Text evaluate(Text dateText) {
        return evaluate(dateText, new Text(DEFAULT_QUARTER_PATTERN));
    }

    public Text evaluate(Text dateText, Text quarterPatternText) {

        String dateString = dateText.toString();
        Date date = !StringUtils.isEmpty(dateString) ? DateUtil.toDate(dateString) : null;

        if (null != date) {
            this.result.set(DateUtil.quarter(date, this.getQuarterPatternString(quarterPatternText)));
        }

        return this.result;
    }

    public Text evaluate(DateWritable dateWrite) {
        return evaluate(dateWrite, new Text(DEFAULT_QUARTER_PATTERN));
    }

    public Text evaluate(DateWritable dateWrite, Text quarterPatternText) {

        if (null != dateWrite) {
            this.result.set(DateUtil.quarter(dateWrite.get(), this.getQuarterPatternString(quarterPatternText)));
        }

        return this.result;
    }

    public Text evaluate(TimestampWritable timestampWrite) {
        return evaluate(timestampWrite, new Text(DEFAULT_QUARTER_PATTERN));
    }

    public Text evaluate(TimestampWritable timestampWrite, Text quarterPatternText) {

        if (null != timestampWrite) {
            this.result
                .set(DateUtil.quarter(timestampWrite.getTimestamp(), getQuarterPatternString(quarterPatternText)));
        }

        return this.result;
    }

    private String getQuarterPatternString(Text quarterPatternText) {
        String quarterPatternString =
            (null == quarterPatternText) ? DEFAULT_QUARTER_PATTERN : quarterPatternText.toString();

        return (!quarterPatternString.contains("yyyy") && !quarterPatternString.contains("QR")
            && !quarterPatternString.contains("qr")) ? DEFAULT_QUARTER_PATTERN : quarterPatternString;
    }

}
