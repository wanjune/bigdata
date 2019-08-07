package org.wanjune.bigdata.udf;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.wanjune.bigdata.util.FullwidthCharacterUtil;

/**
 * UDFHalfwidth2Fullwidth
 *
 * @author wanjune
 * @date 2019/08/07
 */
@Description(
        name = "Halfwidth2Fullwidth",
        value = "Transform Half-width characters to Full-width characters"
)
public class UDFHalfwidth2Fullwidth extends UDF {

    private final Text result = new Text();

    public Text evaluate(NullWritable charactersText) {
        return this.result;
    }

    public Text evaluate(Text charactersText) {
        String charactersString = charactersText.toString();

        if (StringUtils.isEmpty(charactersString)) {
            return this.result;
        }

        this.result.set(FullwidthCharacterUtil.transformHalfwidthToFullwidth(charactersString));
        return this.result;
    }

}
