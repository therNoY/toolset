package pers.mihao.toolset.util;

public class IntegerUtil {


    public static int getRandomIn(int max) {

        return (int) (Math.round(Math.random() * (max - 0)) + 0);
    }

    public static int getRandomIn(int min, int max) {
        return (int) (Math.round(Math.random() * (max - min)) + min);
    }
}
