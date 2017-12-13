package edu.web;

import edu.web.utils.ActionDefinitionUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class FrameworkServlet extends HttpServlet {

    /**
     * Response
     */
    public final static String RESPONSE = "edu.web.FrameWorkServlet.response";

    /**
     * Request
     */
    public final static String REQUEST = "edu.web.FrameWorkServlet.request";

    /**
     * Request scope
     */
    public final static String REQUEST_MAP = "edu.web.FrameWorkServlet.request.map";

    /**
     * Session scope
     */
    public final static String SESSION_MAP = "edu.web.FrameWorkServlet.session.map";

    /**
     * ServletContext scope
     */
    public final static String APPLICATION_MAP = "edu.web.FrameWorkServlet.application.map";


    /**
     * Tomcat, Jetty, JBoss, GlassFish 默认Servlet名称
     * 如果是静态资源或者是控制器无法处理的请求，则交给容器的默认Servlet处理
     */
    private static final String COMMON_DEFAULT_SERVLET_NAME = "default";

    /**
     * 从容器中获取的默认Servlet名
     */
    String defaultServletName;

    final static String ACTION_DEFINITION = "edu.web.actionMapper";

    /**
     * 所有请求控制器映射描述集合
     */
    protected Map<String, ActionDefinition> definitions;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 初始化web容器的默认servlet
        initDefaultServlet(config);
        // 初始化ActionMapper请求映射描述
        initActionDefinition(config);

    }

    /**
     * 初始化web容器的默认Servlet名称
     *
     * @param config
     */
    private void initDefaultServlet(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        defaultServletName = servletContext
                .getInitParameter("defaultServletName");
        if (defaultServletName == null || "".equals(defaultServletName)) {
            if (servletContext.getNamedDispatcher(COMMON_DEFAULT_SERVLET_NAME) != null) {
                defaultServletName = COMMON_DEFAULT_SERVLET_NAME;
            } else {
                throw new IllegalStateException("Unable to locate the default servlet for serving static content. " +
                        "Please set the 'defaultServletName' property explicitly.");
            }
        }
    }

    /**
     * 初始化initActionDefinition(所有Action请求的描述信息)
     *
     * @param config
     */
    private void initActionDefinition(ServletConfig config) throws ServletException {
        // 初始化所有Action的描述定义
        definitions = ActionDefinitionUtil.createActionDefinition();
        // 将所有描述定义存入上下文
        config.getServletContext().setAttribute(ACTION_DEFINITION,
                definitions);
    }

    /**
     * 当请求匹配不到相应的映射描述, 则给容器默认的servlet处理
     *
     * @throws IOException
     * @throws ServletException
     */
    protected void forwardDefaultServlet(HttpServletRequest request,
                                         HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getServletContext().getNamedDispatcher(
                defaultServletName);
        if (rd == null) {
            throw new IllegalStateException(
                    "A RequestDispatcher could not be located for the default servlet '"
                            + this.defaultServletName + "'");
        }
        rd.forward(request, response);
    }
}
