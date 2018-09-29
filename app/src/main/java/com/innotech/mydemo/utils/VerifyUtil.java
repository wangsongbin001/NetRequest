package com.innotech.mydemo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtil {
    /**
     * URL检查<br>
     * <br>
     *
     * @param pInput 要检查的字符串<br>
     * @return boolean   返回检查结果<br>
     */
    public static boolean isUrl(String pInput) {
        if (pInput == null) {
            return false;
        }
        String regEx = "^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }
}
