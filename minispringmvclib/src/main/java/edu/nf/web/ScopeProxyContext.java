package edu.nf.web;

import edu.nf.web.scopeproxy.ApplicationMapHandler;
import edu.nf.web.scopeproxy.RequestMapHandler;
import edu.nf.web.scopeproxy.ScopeHandler;
import edu.nf.web.scopeproxy.SessionMapHandler;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 作用域代理上下文
 */
public class ScopeProxyContext {

    private ScopeHandler handler;
    private Map<String,Object> scopeMap;

    public ScopeProxyContext(String scopeName){
        if(scopeName.equals(FrameworkServlet.REQUEST_MAP)) {
            handler = new RequestMapHandler(new HashMap<String, Object>());
        }
        if(scopeName.equals(FrameworkServlet.SESSION_MAP)) {
            handler = new SessionMapHandler(new HashMap<String, Object>());
        }
        if(scopeName.equals(FrameworkServlet.APPLICATION_MAP)) {
            handler = new ApplicationMapHandler(new HashMap<String, Object>());
        }
    }

    /**
     * 创建作用域代理实例
     * @return
     */
    public Map<String, Object> createScopeProxyMap() {
        scopeMap = (Map<String, Object>) Proxy.newProxyInstance(ScopeProxyContext.class
                .getClassLoader(), new Class<?>[] { Map.class }, handler);
        return scopeMap;
    }
}
