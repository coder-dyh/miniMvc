package edu.nf.web.map;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 请求作用域
 */
public class RequestMap extends HashMap<String, Object>{

    private HttpServletRequest request;

    public RequestMap(HttpServletRequest request){
        this.request = request;
    }

    @Override
    public Object get(Object key) {
        return request.getAttribute(key.toString());
    }

    @Override
    public Object put(String key, Object value) {
        request.setAttribute(key, value);
        return value;
    }

    @Override
    public Object remove(Object key) {
        Object value = request.getAttribute(key.toString());
        request.removeAttribute(key.toString());
        return value;
    }
}
