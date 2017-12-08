package edu.nf.web.convert.handler;

import edu.nf.web.ParamInfo;
import edu.nf.web.ParamsConvertHandler;
import edu.nf.web.convert.ConvertHandlerChain;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;

public class BeanConvertHandler extends ParamsConvertHandler{
    @Override
    public Object handle(ParamInfo paramInfo, ConvertHandlerChain chain) {
        try {
            Object param = paramInfo.getParamType().newInstance();
            BeanUtils.populate(param, getRequest().getParameterMap());
            return param;
        } catch (Throwable e) {
            return chain.execute(paramInfo);
        }
    }
}
