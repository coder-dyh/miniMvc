package edu.web;

import edu.web.map.ApplicationMap;
import edu.web.map.RequestMap;
import edu.web.map.SessionMap;
import edu.web.view.DefaultViewResult;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * mvc核心控制器
 */
public class DispatcherServlet extends FrameworkServlet {

    /**
     * 请求映射处理器
     */
    private HandlerMapping handlerMapping;

    /**
     * Action回调处理器
     */
    private HandlerInvoker handlerInvoker;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initHandlerMapping();
        initHandlerInvoker();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 初始化ActionContext
        initActionContext(request, response);
        // 请求映射,找到匹配的Action描述,返回ActionMapping对象
        ActionMapper mapper = handlerMapping.handle();
        // 如果mapper为null，表示没有匹配的Action描述定义来处理请求,则当前请求交给默认servlet处理
        if(mapper == null){
            forwardDefaultServlet(request, response);
        } else {
            // 执行请求处理服务，并返回试图结果集
            Object viewObject = handlerInvoker.invoke(mapper);
            // 响应视图
            response(viewObject);
            // 清除ActionContext的本地线程副本
            destroyActionContext();
        }
    }

    /**
     * 初始化请求映射处理器
     */
    private void initHandlerMapping() {
        handlerMapping = new HandlerMapping();
    }

    /**
     * 初始化action回调处理器
     */
    private void initHandlerInvoker(){
        handlerInvoker = new HandlerInvoker();
    }

    /**
     * 初始化ActionContext实例,并创建作用域代理
     */
    private void initActionContext(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> contextMap = ActionContext.getContext()
                .getContextMap();
        // 将request对象放入contextMap中
        contextMap.put(REQUEST, request);
        // 将response对象放入contextMap中
        contextMap.put(RESPONSE, response);
        // 构建HttpServletRequest作用域代理,放入contextMap中
        contextMap.put(REQUEST_MAP, new RequestMap(request));
        // 构建HttpSession作用域代理,放入contextMap中
        contextMap.put(SESSION_MAP, new SessionMap(request));
        // 构建ServletContext上下文作用域代理,放入contextMap中
        contextMap.put(APPLICATION_MAP, new ApplicationMap(request));
    }

    /**
     * 响应视图结果
     *
     * @param viewObject
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private void response(Object viewObject) throws IOException, ServletException{
        if (viewObject != null) {
            ViewResult viewResult = (viewObject instanceof ViewResult) ? (ViewResult) viewObject
                    : new DefaultViewResult(viewObject);
            viewResult.execute();
        }
    }

    /**
     * 清除ActionContext的本地线程副本
     */
    private void destroyActionContext(){
        ActionContext.localContext.remove();
    }

}
