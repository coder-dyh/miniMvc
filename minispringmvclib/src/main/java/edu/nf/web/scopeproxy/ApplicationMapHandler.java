package edu.nf.web.scopeproxy;

/**
 * 上下文作用域代理
 */
public class ApplicationMapHandler extends ScopeHandler {

	public ApplicationMapHandler(Object target) {
		super(target);
	}

	void setAttribute(String key, Object value) {
		getLocalRequest().getServletContext().setAttribute(key, value);
	}

	Object getAttribute(String key) {
		return getLocalRequest().getServletContext().getAttribute(key);
	}

	void removeAttribute(String key) {
		getLocalRequest().getServletContext().removeAttribute(key);
	}

}