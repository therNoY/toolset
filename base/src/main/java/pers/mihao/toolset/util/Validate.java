package pers.mihao.toolset.util;

public class Validate {

    public static boolean isUrl (String url){
        if (!url.startsWith("https://") && !url.startsWith("http://"))
            return false;
        if (url.contains("?") || url.contains("&"))
            return false;
        return true;
    }
}
