package org.wanjune.bigdata.udf;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.wanjune.bigdata.util.CharacterUtil;

/**
 * UDFQuarter
 *
 * @author wanjune
 * @date 2019/08/07
 */
@Description(name = "cleartext", value = "_FUNC_(text) - Returns the clear text",
    extended = "1.Transform Full-width characters to Half-width characters;\n2.Remove the Control characters;\n3.Remove the [Space] character;\n4.Remove the [DEL] character.\nExample:\n   > SELECT _FUNC_('１-AＤ 1　1 01') FROM src LIMIT 1;\n  1-AD1101\n")
public class UDFClearText extends UDF {
    private final Text result = new Text();

    public UDFClearText() {}

    public Text evaluate(NullWritable text) {
        return this.result;
    }

    public Text evaluate(Text text) {

        String textString = text.toString();

        if (StringUtils.isEmpty(textString)) {
            return this.result;
        }

        this.result.set(CharacterUtil.clearText(textString));
        return this.result;
    }

}
