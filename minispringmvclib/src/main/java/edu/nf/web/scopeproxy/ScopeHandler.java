package edu.nf.web.scopeproxy;

import edu.nf.web.ActionContext;
import edu.nf.web.FrameworkServlet;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class ScopeHandler implements InvocationHandler {

	private Object target;

	public ScopeHandler(Object target) {
		this.target = target;
	}

	HttpServletRequest getLocalRequest() {
		return (HttpServletRequest) ActionContext.getContext().get(
				FrameworkServlet.REQUEST);
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		if ("put".equals(method.getName())) {
			setAttribute(args[0].toString(), args[1]);
		} else if ("get".equals(method.getName())) {
			return getAttribute(args[0].toString());
		} else if ("remove".equals(method.getName())) {
			removeAttribute(args[0].toString());
		}
		return method.invoke(target, args);
	}
	
	abstract void setAttribute(String key, Object value);
	
	abstract Object getAttribute(String key);
	
	abstract void removeAttribute(String key);

}
