package edu.nf.web.scopeproxy;

/**
 * 请求作用域代理
 */
public class RequestMapHandler extends ScopeHandler{


    public RequestMapHandler(Object target) {
        super(target);
    }

    @Override
    void setAttribute(String key, Object value) {
        getLocalRequest().setAttribute(key, value);
    }

    @Override
    Object getAttribute(String key) {
        return getLocalRequest().getAttribute(key);
    }

    @Override
    void removeAttribute(String key) {
        getLocalRequest().removeAttribute(key);
    }
}
