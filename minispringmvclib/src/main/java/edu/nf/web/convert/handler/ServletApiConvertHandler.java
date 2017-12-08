package edu.nf.web.convert.handler;

import edu.nf.web.ActionContext;
import edu.nf.web.FrameworkServlet;
import edu.nf.web.ParamInfo;
import edu.nf.web.ParamsConvertHandler;
import edu.nf.web.convert.ConvertHandlerChain;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletApiConvertHandler extends ParamsConvertHandler{
    @Override
    public Object handle(ParamInfo paramInfo, ConvertHandlerChain chain) {
        if (paramInfo.getParamType() == HttpServletRequest.class) {
            return getRequest();
        } else if (paramInfo.getParamType() == HttpServletResponse.class) {
            return ActionContext.getContext().get(FrameworkServlet.RESPONSE);
        } else if (paramInfo.getParamType() == HttpSession.class) {
            return getRequest().getSession();
        } else if (paramInfo.getParamType() == ServletContext.class) {
            return getRequest().getServletContext();
        }
        return chain.execute(paramInfo);
    }
}
