package org.wanjune.bigdata.util;

import org.apache.commons.lang3.StringUtils;

/**
 * CharacterUtil
 *
 * @author wanjune
 * @date 2019/08/07
 */
public class CharacterUtil {

    // Control (ASCII) : from (0) to (31)
    private static final char ASCII_CTRL_END = 31;

    // Half-width[Space] (ASCII) : 32
    private static final char ASCII_HALFWIDTH_SPACE = ' ';

    // Half-width (ASCII) : from [!](33) to [~](126)
    private static final char ASCII_HALFWIDTH_START = 33;
    private static final char ASCII_HALFWIDTH_END = 126;

    // DEL (ASCII) : 127
    private static final char ASCII_DEL = 127;

    // Full-width[Space] (ASCII) : 12288
    private static final char ASCII_FULLWIDTH_SPACE = 12288;

    // Full-width (ASCII) : from [！](65281) to [～](65374)
    private static final char ASCII_FULLWIDTH_START = 65281;
    private static final char ASCII_FULLWIDTH_END = 65374;

    // The offset between Full-width and Half-width
    private static final int ASCII_HALF2FULL_OFFSET = 65248;


    /**
     * Transform Half-width characters to Full-width characters
     *
     * @param characters : Half-width characters
     * @return Full-width characters
     */
    public static String transformHalfwidthToFullwidth(String characters) {
        if (StringUtils.isEmpty(characters)) {
            return characters;
        }
        StringBuilder sbBuffer = new StringBuilder(characters.length());
        char[] charItemArray = characters.toCharArray();
        for (char charItem : charItemArray) {
            if (ASCII_HALFWIDTH_SPACE == charItem) {
                // Half-width [Blank Space] (ASCII) : (32)
                sbBuffer.append(ASCII_FULLWIDTH_SPACE);
            } else if ((charItem >= ASCII_HALFWIDTH_START) && (charItem <= ASCII_HALFWIDTH_END)) {
                // Half-width (ASCII) : from [!](33) to [~](126)
                sbBuffer.append((char) (charItem + ASCII_HALF2FULL_OFFSET));
            } else {
                // Others
                sbBuffer.append(charItem);
            }
        }
        return sbBuffer.toString();
    }

    /**
     * Transform Full-width characters to Half-width characters
     *
     * @param characters : Full-width characters
     * @return Half-width characters
     */
    public static String transformFullwidthToHalfwidth(String characters) {
        if (StringUtils.isEmpty(characters)) {
            return characters;
        }
        StringBuilder sbBuffer = new StringBuilder(characters.length());
        char[] charItemArray = characters.toCharArray();
        for (char charItem : charItemArray) {
            if (ASCII_FULLWIDTH_SPACE == charItem) {
                // Full-width [Blank Space] (ASCII) : (12288)
                sbBuffer.append(ASCII_HALFWIDTH_SPACE);
            } else if (charItem >= ASCII_FULLWIDTH_START && charItem <= ASCII_FULLWIDTH_END) {
                // Full-width (ASCII) : from [！](65281) to [～](65374)
                sbBuffer.append((char) (charItem - ASCII_HALF2FULL_OFFSET));
            } else {
                // Others
                sbBuffer.append(charItem);
            }
        }
        return sbBuffer.toString();
    }

    /**
     * Clear up the text
     * 1.Transform Full-width characters to Half-width characters
     * 2.Remove the Control characters
     * 3.Remove the [Blank Space] character
     * 4.Remove the [DEL] character
     *
     * @param characters : Full-width characters
     * @return Half-width characters
     */
    public static String clearText(String characters) {
        if (StringUtils.isEmpty(characters)) {
            return characters;
        }
        StringBuilder sbBuffer = new StringBuilder(characters.length());
        char[] charItemArray = characters.toCharArray();
        char iChar;
        for (char charItem : charItemArray) {
            if (ASCII_FULLWIDTH_SPACE == charItem) {
                // Full-width [Blank Space] (ASCII) : (12288)
                iChar = ASCII_HALFWIDTH_SPACE;
            } else if (charItem >= ASCII_FULLWIDTH_START && charItem <= ASCII_FULLWIDTH_END) {
                // Full-width (ASCII) : from [！](65281) to [～](65374)
                iChar = (char) (charItem - ASCII_HALF2FULL_OFFSET);
            } else {
                // Others
                iChar = charItem;
            }

            if (iChar > ASCII_CTRL_END && ASCII_HALFWIDTH_SPACE != iChar && ASCII_DEL != iChar) {
                sbBuffer.append(iChar);
            }
        }
        return sbBuffer.toString();
    }

}
