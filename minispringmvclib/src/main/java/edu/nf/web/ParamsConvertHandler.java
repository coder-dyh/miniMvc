package edu.nf.web;

import edu.nf.web.convert.ConvertHandlerChain;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

/**
 * 类型转换，用于映射不同的数据类型
 */
public abstract class ParamsConvertHandler {

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) ActionContext
                .getContext().get(FrameworkServlet.REQUEST);

    }

    public abstract Object handle(Parameter parameter, ConvertHandlerChain chain);
}
