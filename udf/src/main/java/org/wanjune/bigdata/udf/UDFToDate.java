package org.wanjune.bigdata.udf;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.wanjune.bigdata.util.DateUtil;

/**
 * UDFToDate
 *
 * @author wanjune
 * @date 2019/08/07
 */
public class UDFToDate extends UDF {
    private final DateWritable result = new DateWritable();

    public UDFToDate() {}

    public DateWritable evaluate(NullWritable dateText) {
        return null;
    }

    public DateWritable evaluate(NullWritable dateText, Text datePatternText) {
        return null;
    }

    public DateWritable evaluate(Text dateText) {

        String dateString = dateText.toString();

        if (StringUtils.isEmpty(dateString)) {
            return null;
        }

        Date rdate = DateUtil.toDate(dateString);

        if (null == rdate) {
            return null;
        }

        this.result.set(new java.sql.Date(rdate.getTime()));
        return this.result;
    }

    public DateWritable evaluate(Text dateText, Text datePatternText) {

        String dateString = dateText.toString();
        String datePatternString = datePatternText.toString();

        if (StringUtils.isEmpty(dateString)) {
            return null;
        }

        Date rdate = DateUtil.toDate(dateString, datePatternString);

        if (null == rdate) {
            return null;
        }

        this.result.set(new java.sql.Date(rdate.getTime()));
        return this.result;
    }

}
