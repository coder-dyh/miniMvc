package edu.nf.web.convert.handler;

import edu.nf.web.ParamsConvertHandler;
import edu.nf.web.convert.ConvertHandlerChain;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Parameter;

/**
 * 转换基本类型以及包装类型
 */
public class BasicTypeConvertHandler extends ParamsConvertHandler {
    @Override
    public Object handle(Parameter paramInfo, ConvertHandlerChain chain) {
        Object param = (paramInfo.getType().isArray()) ? getRequest()
                .getParameterValues(paramInfo.getName()) : getRequest()
                .getParameter(paramInfo.getName());
        if (param == null) {
            return chain.execute(paramInfo);
        }
        Object value = ConvertUtils.convert(param, paramInfo.getType());
        if (value == null && paramInfo.getType().isPrimitive()){
            throw new RuntimeException(value + " can not be converted to "+paramInfo.getType().getName());
        }
        return value;
    }
}
