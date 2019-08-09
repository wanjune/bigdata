package org.wanjune.bigdata.udf;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.wanjune.bigdata.util.CharacterUtil;

/**
 * UDFFullwidth2Halfwidth
 *
 * @author wanjune
 * @date 2019/08/07
 */
@Description(
        name = "fullwidth2Halfwidth",
        value = "_FUNC_(text) - Returns Half-width string",
        extended = "text is a string with Full-width.\nExample:\n   > SELECT _FUNC_('１３４２３６７０９３１') FROM src LIMIT 1;\n  13423670931"
)
public class UDFFullwidth2Halfwidth extends UDF {
    private final Text result = new Text();

    public UDFFullwidth2Halfwidth() {
    }

    public Text evaluate(NullWritable charactersText) {
        return this.result;
    }

    public Text evaluate(Text charactersText) {

        String charactersString = charactersText.toString();

        if (StringUtils.isEmpty(charactersString)) {
            return this.result;
        }

        this.result.set(CharacterUtil.transformFullwidthToHalfwidth(charactersString));
        return this.result;
    }

}
