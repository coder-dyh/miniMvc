package edu.web.view;

import edu.web.ViewResult;
import edu.web.utils.JsonUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JSON视图
 */
public class JsonView extends ViewResult {

    public final static String CONTENT_TYPE = "application/json;charset=utf-8";

    private String json;

    public JsonView(Object arg) {
        json = JsonUtil.toJson(arg);
    }

    public JsonView(Object arg, String format) {
        json = JsonUtil.toJson(arg, format);
    }

    @Override
    protected void execute() throws IOException {
        HttpServletResponse response = getResponse();
        response.setContentType(CONTENT_TYPE);
        response.getWriter().println(json);
    }
}
