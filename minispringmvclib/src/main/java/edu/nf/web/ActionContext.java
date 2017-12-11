package edu.nf.web;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Action上下文，不同请求对应自己的上下文实例
 */
public class ActionContext {

    /**
     * ActionContext的本地线程副本
     */
    final static ThreadLocal<ActionContext> localContext = new ThreadLocal<ActionContext>();

    private ActionContext() {}

    /**
     * ActionContext上下文的Map结构
     */
    private Map<String, Object> contextMap = new HashMap<String, Object>();

    /**
     * 获取当前线程绑定的ActionContext
     *
     * @return
     */
    public static ActionContext getContext() {
        if (localContext.get() == null) {
            // 如果当前线程上没有绑定ActionContext,则创建一个并绑定当前线程
            localContext.set(new ActionContext());
        }

        // 返回当前线程的ActionContext对象
        return localContext.get();
    }

    Map<String, Object> getContextMap() {
        return contextMap;
    }

    /**
     * 获取Request代理
     * @return
     */
    public Map<String, Object> getRequest(){
        return (Map<String, Object>) get(FrameworkServlet.REQUEST_MAP);
    }

    /**
     * 获取Session代理
     * @return
     */
    public Map<String, Object> getSession() {
        return (Map<String, Object>) get(FrameworkServlet.SESSION_MAP);
    }

    /**
     * 获取ServletContext代理
     * @return
     */
    public Map<String, Object> getApplication() {
        return (Map<String, Object>) get(FrameworkServlet.APPLICATION_MAP);
    }

    /**
     * 从contextMap中获取信息
     */
    public Object get(String key) {
        return contextMap.get(key);
    }

    // -------------Servlet API-------------
    /**
     * 获取资源绝对路径
     *
     * @param arg0
     * @return
     */
    public String getRealPath(String arg0) {
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getServletContext().getRealPath(arg0);
    }

    /**
     * 获取上下文路径
     * @return
     */
    public String getContextPath() {
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getServletContext().getContextPath();
    }

    /**
     * 获取请求参数
     * @return
     */
    public String getQueryString(){
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getQueryString();
    }

    /**
     * 获取Servlet路径
     * @return
     */
    public String getServletPath(){
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getServletPath();
    }

    /**
     * 获取请求路径信息
     * @return
     */
    public String getPathInfo(){
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getPathInfo();
    }

    /**
     * 获取请求头信息
     * @param arg0
     * @return
     */
    public String getHeader(String arg0){
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getHeader(arg0);
    }

    /**
     * 获取请求类型
     * @return
     */
    public String getContentType(){
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getContentType();
    }

    /**
     * 获取cookies
     * @return
     */
    public Cookie[] getCookies(){
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getCookies();
    }

    /**
     * 添加cookie
     */
    public void addCookie(Cookie cookie) {
        HttpServletResponse response = (HttpServletResponse) get(FrameworkServlet.RESPONSE);
        response.addCookie(cookie);
    }

    /**
     * 获取Servlet的输入流
     * @return
     * @throws IOException
     */
    public ServletInputStream getInputStream() throws IOException {
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getInputStream();
    }

    /**
     * 获取所有请求参数
     */
    public Map<String, String[]> getParameters() {
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getParameterMap();
    }

    /**
     * 获取sessionID
     */
    public String getSessionId() {
        HttpServletRequest request = (HttpServletRequest) get(FrameworkServlet.REQUEST);
        return request.getSession().getId();
    }

}
