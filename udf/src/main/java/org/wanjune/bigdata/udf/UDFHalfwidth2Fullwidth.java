package org.wanjune.bigdata.udf;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.wanjune.bigdata.util.CharacterUtil;

/**
 * UDFHalfwidth2Fullwidth
 *
 * @author wanjune
 * @date 2019/08/07
 */
@Description(
        name = "halfwidth2Fullwidth",
        value = "_FUNC_(text) - Returns Full-width string",
        extended = "text is a string with Half-width.\nExample:\n   > SELECT _FUNC_('13423670931') FROM src LIMIT 1;\n  １３４２３６７０９３１"
)
public class UDFHalfwidth2Fullwidth extends UDF {
    private final Text result = new Text();

    public UDFHalfwidth2Fullwidth() {
    }

    public Text evaluate(NullWritable charactersText) {
        return this.result;
    }

    public Text evaluate(Text charactersText) {
        String charactersString = charactersText.toString();

        if (StringUtils.isEmpty(charactersString)) {
            return this.result;
        }

        this.result.set(CharacterUtil.transformHalfwidthToFullwidth(charactersString));
        return this.result;
    }

}
