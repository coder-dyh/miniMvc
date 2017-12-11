package edu.nf.web.convert.handler;

import edu.nf.web.ActionContext;
import edu.nf.web.FrameworkServlet;
import edu.nf.web.ParamsConvertHandler;
import edu.nf.web.convert.ConvertHandlerChain;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;

public class ServletApiConvertHandler extends ParamsConvertHandler{
    @Override
    public Object handle(Parameter paramInfo, ConvertHandlerChain chain) {
        if (paramInfo.getType().equals(HttpServletRequest.class)) {
            return getRequest();
        } else if (paramInfo.getType().equals(HttpServletResponse.class)) {
            return ActionContext.getContext().get(FrameworkServlet.RESPONSE);
        } else if (paramInfo.getType().equals(HttpSession.class)) {
            return getRequest().getSession();
        } else if (paramInfo.getType().equals(ServletContext.class)) {
            return getRequest().getServletContext();
        }
        return chain.execute(paramInfo);
    }
}
