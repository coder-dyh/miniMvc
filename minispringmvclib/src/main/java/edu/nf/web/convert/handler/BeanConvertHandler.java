package edu.nf.web.convert.handler;

import edu.nf.web.ParamsConvertHandler;
import edu.nf.web.convert.ConvertHandlerChain;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Parameter;

public class BeanConvertHandler extends ParamsConvertHandler{
    @Override
    public Object handle(Parameter paramInfo, ConvertHandlerChain chain) {
        try {
            Object param = paramInfo.getType().newInstance();
            BeanUtils.populate(param, getRequest().getParameterMap());
            return param;
        } catch (Throwable e) {
            return chain.execute(paramInfo);
        }
    }
}
