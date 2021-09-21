package cn.gyw.handwritten.gspring.aop.config;

public class GAopConfig {

    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectThrowExceptionName;

    public String getPointCut() {
        return pointCut;
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }

    public String getAspectBefore() {
        return aspectBefore;
    }

    public void setAspectBefore(String aspectBefore) {
        this.aspectBefore = aspectBefore;
    }

    public String getAspectAfter() {
        return aspectAfter;
    }

    public void setAspectAfter(String aspectAfter) {
        this.aspectAfter = aspectAfter;
    }

    public String getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(String aspectClass) {
        this.aspectClass = aspectClass;
    }

    public String getAspectAfterThrow() {
        return aspectAfterThrow;
    }

    public void setAspectAfterThrow(String aspectAfterThrow) {
        this.aspectAfterThrow = aspectAfterThrow;
    }

    public String getAspectThrowExceptionName() {
        return aspectThrowExceptionName;
    }

    public void setAspectThrowExceptionName(String aspectThrowExceptionName) {
        this.aspectThrowExceptionName = aspectThrowExceptionName;
    }

    @Override
    public String toString() {
        return "GAopConfig{" +
                "pointCut='" + pointCut + '\'' +
                ", aspectBefore='" + aspectBefore + '\'' +
                ", aspectAfter='" + aspectAfter + '\'' +
                ", aspectClass='" + aspectClass + '\'' +
                ", aspectAfterThrow='" + aspectAfterThrow + '\'' +
                ", aspectThrowExceptionName='" + aspectThrowExceptionName + '\'' +
                '}';
    }
}
