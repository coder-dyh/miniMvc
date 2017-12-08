package edu.nf.web;

/**
 * 请求方法的参数信息
 */
public class ParamInfo {

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数类型
     */
    private Class<?> paramType;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }
}
