package pers.mihao.toolset.dto;

public class MyException extends RuntimeException{
    private Integer code;
    private String mes;

    public MyException(Integer code) {
        this.code = code;
    }


    public MyException(String mes) {
        super(mes);
        this.mes = mes;
    }

    public MyException(String mes, Exception e) {
        super(mes);
        super.setStackTrace(e.getStackTrace());
        this.mes = mes;
    }

    public Integer getCode() {
        return code;
    }

    public String getMes() {
        return mes;
    }
}
