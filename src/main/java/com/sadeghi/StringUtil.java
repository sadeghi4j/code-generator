package com.sadeghi;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Ali Sadeghi
 * at 2016/07/07 - 11:03
 */
public class StringUtil {

    public static String camelCaseToUnderscore(String prefix, String camelCase) {
        String[] split = StringUtils.splitByCharacterTypeCamelCase(camelCase);
        String join = prefix + StringUtils.join(split, "_").toUpperCase();
        return join.toUpperCase();
    }

    public static String underscoreToCamelCase(String prefixToRemove, boolean lowercaseFirstLetter, String underscore) {
        underscore = underscore.replace(prefixToRemove, "");
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (String s : underscore.split("_")) {
            if (first && lowercaseFirstLetter) {
                sb.append(Character.toLowerCase(s.charAt(0)));
                first = false;
            } else {
                sb.append(Character.toUpperCase(s.charAt(0)));
            }
            if (s.length() > 1) {
                sb.append(s.substring(1, s.length()).toLowerCase());
            }
        }
        return sb.toString();
    }

}
