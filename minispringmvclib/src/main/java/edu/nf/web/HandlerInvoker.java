package edu.nf.web;

import edu.nf.web.convert.ConvertHandlerChain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

/**
 * 请求回调处理器
 */
public class HandlerInvoker {

    Object invoke(ActionMapper mapper) {
        newActionInstance(mapper);
        convertParams(mapper);
        return invokeMethod(mapper);
    }

    /**
     * 构建控制器实例
     */
    private void newActionInstance(ActionMapper mapper){
        Class<?> clazz = mapper.getDefinition().getControllerClass();
        try {
            mapper.setTarget(clazz.newInstance());
        } catch (InstantiationException e) {
            throw new RuntimeException("Create controller instance fail.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Create controller instance fail.", e);
        }
    }

    /**
     * 参数映射
     */
    private void convertParams(ActionMapper mapper){
        ActionDefinition definition = mapper.getDefinition();
        Object[] params = new Object[definition.getControllerMethod().getParameterCount()];
        for (int i = 0; i < params.length; i++) {
            Parameter paramInfo = definition.getControllerMethod().getParameters()[i];
            params[i] = new ConvertHandlerChain().execute(paramInfo);
            // 如果映射参数不成功,且方法参数类型是基本类型,则抛出异常信息
            if (params[i] == null && paramInfo.getType().isPrimitive()) {
                throw new RuntimeException("Optional " + paramInfo.getType() + " parameter " + paramInfo.getName() +
                        " is present but cannot be translated into a null value due to being declared as a primitive type. ");
            }
        }
        mapper.setParams(params);
    }

    /**
     * 调用控制器方法处理请求
     */
    private Object invokeMethod(ActionMapper mapper){
        Object viewObject = null;
        try {
            viewObject = mapper.getDefinition().getControllerMethod()
                    .invoke(mapper.getTarget(), mapper.getParams());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Invoke target method fail.", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Invoke target method fail.", e);
        }
        return viewObject;
    }

}
