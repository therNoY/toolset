package pers.mihao.toolset.common.vo;

public class RespResult {
    private Integer resCode;
    private String resMes;
    private Object resVal;

    public RespResult() {
    }

    public RespResult(Integer resCode, String resMes) {
        this.resCode = resCode;
        this.resMes = resMes;
    }

    public RespResult(Integer resCode, String resMes, Object resVal) {
        this.resCode = resCode;
        this.resMes = resMes;
        this.resVal = resVal;
    }

    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResMes() {
        return resMes;
    }

    public void setResMes(String resMes) {
        this.resMes = resMes;
    }

    public Object getResVal() {
        return resVal;
    }

    public void setResVal(Object resVal) {
        this.resVal = resVal;
    }

    @Override
    public String toString() {
        return "RespResult{" +
                "resCode=" + resCode +
                ", resMes='" + resMes + '\'' +
                ", resVal=" + resVal +
                '}';
    }
}
