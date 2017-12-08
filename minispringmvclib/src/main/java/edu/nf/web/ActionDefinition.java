package edu.nf.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 控制器的请求映射描述定义
 */
public class ActionDefinition {

    /**
     * 控制器的Class对象
     */
    private Class<?> controllerClass;

    /**
     * 处理请求的方法
     */
    private Method controllerMethod;

    /**
     * 请求方法的参数信息集合
     */
    private List<ParamInfo> paramInfos = new ArrayList<>();

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }

    public void setControllerMethod(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public List<ParamInfo> getParamInfos() {
        return paramInfos;
    }

    public void setParamInfos(List<ParamInfo> paramInfos) {
        this.paramInfos = paramInfos;
    }
}
