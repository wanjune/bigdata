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
    private final Text result = new Text();

    public UDFQuarter() {}

    public Text evaluate(NullWritable dateText) {
        return this.result;
    }

    public Text evaluate(NullWritable dateText, Text quarterPatternText) {
        return this.result;
    }

    public Text evaluate(Text dateText) {
        return evaluate(dateText, new Text("yyyyQR"));
    }

    public Text evaluate(Text dateText, Text quarterPatternText) {

        String dateString = dateText.toString();
        String quarterPatternString = quarterPatternText.toString();

        if (StringUtils.isEmpty(dateString)) {
            return this.result;
        }

        Date date = DateUtil.toDate(dateString);

        if (null == date) {
            return this.result;
        }

        this.result.set(DateUtil.quarter(date, quarterPatternString));
        return this.result;
    }

    public Text evaluate(DateWritable dateWrite) {
        return evaluate(dateWrite, new Text("yyyyQR"));
    }

    public Text evaluate(DateWritable dateWrite, Text quarterPatternText) {

        String quarterPatternString = quarterPatternText.toString();

        if (dateWrite == null) {
            return this.result;
        }

        this.result.set(DateUtil.quarter(dateWrite.get(), quarterPatternString));
        return this.result;
    }

    public Text evaluate(TimestampWritable timestampWrite) {
        return evaluate(timestampWrite, new Text("yyyyQR"));
    }

    public Text evaluate(TimestampWritable timestampWrite, Text quarterPatternText) {

        String quarterPatternString = quarterPatternText.toString();

        if (timestampWrite == null) {
            return this.result;
        }

        this.result.set(DateUtil.quarter(timestampWrite.getTimestamp(), quarterPatternString));
        return this.result;
    }

}
