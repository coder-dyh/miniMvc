package edu.nf.web.convert.handler;

import edu.nf.web.ParamInfo;
import edu.nf.web.ParamsConvertHandler;
import edu.nf.web.convert.ConvertHandlerChain;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * 转换基本类型以及包装类型
 */
public class BasicTypeConvertHandler extends ParamsConvertHandler {
    @Override
    public Object handle(ParamInfo paramInfo, ConvertHandlerChain chain) {
        Object param = (paramInfo.getParamType().isArray()) ? getRequest()
                .getParameterValues(paramInfo.getParamName()) : getRequest()
                .getParameter(paramInfo.getParamName());
        if (param == null) {
            return chain.execute(paramInfo);
        }
        Object value = ConvertUtils.convert(param, paramInfo.getParamType());
        if (value == null && paramInfo.getParamType().isPrimitive()){
            throw new RuntimeException(value + " can not be converted to "+paramInfo.getParamType().getName());
        }
        return value;
    }
}
