package pers.mihao.toolset.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.UUID;

public class StringUtil {
    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public final static String parseMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            logger.error("generate md5 error, {}", s, e);
            return null;
        }
    }

    /**
     * 获取UUID
     * @return uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 判断一个字符串不为空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string != null && !string.equals(""))
            return false;
        return true;
    }

    /**
     * 驼峰转下划线大写
     * @param string
     * @return
     */
    public static String humpToUnderscore(String string) {
        // 1.将type 转成大写
        char[] types = string.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : types) {
            if ('a' <= c && c <= 'z') {
                sb.append(Character.toUpperCase(c));
            }else if ('A' <= c && c <= 'Z' ){
                sb.append("_").append(c);
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线大写转驼峰
     * @param string
     * @return
     */
    public static String underscoreToHump(String string) {
        String type = string.toLowerCase();
        char[] typeChar = type.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < typeChar.length; i++) {
            char c = typeChar[i];
            if (c != '_') {
                if (i > 0 && typeChar[i-1] == '_' && sb.length() > 1) {
                    // 只有当他的前一位是"_" 才保持大写
                    sb.append(Character.toUpperCase(c));
                }else {
                    // 其他情况全部小写
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }


}
