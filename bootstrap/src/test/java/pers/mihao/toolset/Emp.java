package pers.mihao.toolset;

public class Emp implements Cloneable{

    public static void main(String[] args) {
        System.out.println(inc());
    }

    public static int inc(){

        int i = 0;

        try {
            int b = 1 / i;
        } catch (Exception e) {
            e.printStackTrace();
            return i ++;
        }finally {
            System.out.println("fin");
            i ++;
        }
            return i;
    }

}
