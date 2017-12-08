package edu.cj.interceptor;

import org.apache.commons.beanutils.BeanUtils;

import edu.cj.context.ActionContext;
import edu.cj.invocation.ActionInvocation;
import edu.cj.stack.ValueStack;

/**
 * 参数拦截器，将请求参数封装到action属性中
 * 
 * @author david
 */
public class ParametersInterceptor implements Interceptor {

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) {
		ActionContext ac = invocation.getActionContext();
		ValueStack valueStack = ac.getValueStack();
		// 获取action对象
		Object action = valueStack.peek();
		try {
			// 将请求参数数据填充到action对象中,这里只做了简单的数据映射处理
			BeanUtils.populate(action, ac.getRequest().getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invocation.invoke();
	}

	@Override
	public void destory() {
	}

}
