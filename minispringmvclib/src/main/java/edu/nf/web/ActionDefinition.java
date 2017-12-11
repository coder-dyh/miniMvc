package edu.nf.web;

import java.lang.reflect.Method;

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
}
