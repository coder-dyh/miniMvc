package edu.cj.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.cj.stack.ValueStack;

/**
 * Action的上下文对象。主要是用来取得java web的相关信息，比如Request，Response，Session等。
 * 
 * @author david
 */
public class ActionContext {

	public static final String REQUEST = "edu.cj.request";
	public static final String RESPONSE = "edu.cj.response";
	public static final String SESSION = "edu.cj.session";
	public static final String APPLICATION = "edu.cj.application";
	public static final String PARAMETERS = "edu.cj.parameters";
	public static final String VALUESTACK = "edu.cj.valuestack";

	private Map<String, Object> context;
	public static ThreadLocal<ActionContext> actionContext = new ThreadLocal<ActionContext>();

	//action构造函数
	public ActionContext(HttpServletRequest request, HttpServletResponse response, Object action) {
		context = new HashMap<String, Object>();
		// 准备域
		// request
		context.put(REQUEST, request);
		// response
		context.put(RESPONSE, response);
		// session
		context.put(SESSION, request.getSession());
		// application
		context.put(APPLICATION, request.getSession().getServletContext());
		// parameters
		context.put(PARAMETERS, request.getParameterMap());

		// valuestack,暂时值栈只在ParametersIntercepter中有用。
		ValueStack vs = new ValueStack();
		// 将action压入栈顶
		vs.push(action);
		// 将ValueStack放入request域中
		request.setAttribute(VALUESTACK, vs);
		// 将ValueStack放入context域中
		context.put(VALUESTACK, vs);


		// 把创建好的ActionContext对象放入到threadlocal中
		actionContext.set(this);
	}

	/**
	 * @return 当前线程对应的ActionContext对象
	 */
	public static ActionContext getContext() {
		return actionContext.get();
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) context.get(REQUEST);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) context.get(RESPONSE);
	}

	public HttpSession getSession() {
		return (HttpSession) context.get(SESSION);
	}

	public ServletContext getApplication() {
		return (ServletContext) context.get(APPLICATION);
	}

	public Map<String, String[]> getParams() {
		return (Map<String, String[]>) context.get(PARAMETERS);
	}

	public ValueStack getValueStack() {
		return (ValueStack) context.get(VALUESTACK);
	}

}
