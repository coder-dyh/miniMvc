package edu.web.convert.handler;

import edu.web.ActionContext;
import edu.web.FrameworkServlet;
import edu.web.ParamsConvertHandler;
import edu.web.convert.ConvertHandlerChain;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;

public class ServletApiConvertHandler extends ParamsConvertHandler {
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
