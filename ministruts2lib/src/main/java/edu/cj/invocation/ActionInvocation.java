package edu.cj.invocation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.cj.config.ActionConfig;
import edu.cj.context.ActionContext;
import edu.cj.interceptor.Interceptor;

/**
 * 拦截器链和action的调用类
 * 
 * @author david
 */
public class ActionInvocation {
	// 拦截器链
	private Iterator<Interceptor> interceptors;
	// 即将要被调用的action实例
	private Object action;
	// action配置信息
	private ActionConfig config;
	// ActionContext
	private ActionContext actionContext;

	/**
	 * 准备各种需要的数据
	 * 
	 * @param interceptorClassNames
	 *            存放着拦截器链每个拦截器的全限定类名
	 * @param config
	 *            需要调用的action的配置信息
	 * @param request
	 * @param response
	 */
	public ActionInvocation(List<String> interceptorClassNames, ActionConfig config, HttpServletRequest request,
			HttpServletResponse response) {
		// 准备Interceptor链
		if (interceptorClassNames != null && interceptorClassNames.size() > 0) {
			List<Interceptor> list = new ArrayList<Interceptor>();
			for (String className : interceptorClassNames) {
				try {
					Interceptor interceptor = (Interceptor) Class.forName(className).newInstance();
                    //列在这里只是表明拦截器的init调用的时机与拦截器的intercept方法不一起，但这里的实现不好，会每次请求时都调用，所以注释掉
					//interceptor.init();
					list.add(interceptor);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("创建Interceptor失败：" + className);
				}
			}
			interceptors = list.iterator();
		}

		// 准备action实例
		this.config = config;
		try {
			action = Class.forName(config.getClassName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建Action失败：" + config.getClassName());
		}

		// 创建ActionContext
		actionContext = new ActionContext(request, response, action);
	}
	
	/**
	 * 调用拦截器链和action处理方法
	 *
	 * @return
	 */
	public String invoke() {
		// 结果串
		String result = null;
		// 判断拦截器链是否还有下一个拦截器或者未被拦截器拦截（result不等于null的话说明已经有拦截器直接返回结果串了）
		if (interceptors!=null && interceptors.hasNext() && result==null) {
			// 有下一个拦截器，调用下一个拦截器的拦截方法
			Interceptor next = interceptors.next();
			result = next.intercept(this);
		} else {
			// 没有下一个拦截器，调用action实例的处理方法
			// 获取处理的方法名
			String methodName = config.getMethod();
			try {
				Method method = action.getClass().getMethod(methodName);
				result = (String) method.invoke(action);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("action方法调用失败：" + methodName);
			}

		}
		return result;
	}
	
	public ActionContext getActionContext() {
		return actionContext;
	}
}
