package edu.cj.interceptor;

import edu.cj.invocation.ActionInvocation;

/**
 * 拦截器接口
* @author david
 */
public interface Interceptor {
	/**
	 * 执行拦截器初始化工作
	 */
	 void init();
	/**
	 * 拦截功能，在请求前或请求后执行一些处理
	 * @param invocation
	 * @return 
	 */
	public String intercept(ActionInvocation invocation);
	/**
	 * 让拦截器做一些释放资源工作
	 */
	public void destory();
}
