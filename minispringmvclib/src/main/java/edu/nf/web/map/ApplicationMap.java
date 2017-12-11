package edu.nf.web.map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 上下文作用域
 */
public class ApplicationMap extends HashMap<String, Object>{

    private ServletContext servletContext;

    public ApplicationMap(HttpServletRequest request){
        this.servletContext = request.getServletContext();
    }

    @Override
    public Object get(Object key) {
        return servletContext.getAttribute(key.toString());
    }

    @Override
    public Object put(String key, Object value) {
        servletContext.setAttribute(key, value);
        return value;
    }

    @Override
    public Object remove(Object key) {
        Object value = servletContext.getAttribute(key.toString());
        servletContext.removeAttribute(key.toString());
        return value;
    }
}
