package pers.mihao.toolset.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getNow(){
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}
