package pers.mihao.toolset.util;

public class EnumUtil {

    /**
     * 将String 转成存在的枚举类型
     *
     * @param enumType
     * @param type
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String type) {
        return Enum.valueOf(enumType, StringUtil.humpToUnderscore(type));
    }
}
