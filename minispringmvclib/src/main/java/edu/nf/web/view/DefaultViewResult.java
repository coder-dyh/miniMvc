package edu.nf.web.view;

import edu.nf.web.ViewResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认视图，当请求处理方法返回的不是ViewResult的视图对象时
 * 则使用默认视图响应客户端
 */
public class DefaultViewResult extends ViewResult {
	
	final static String CONTENT_TYPE = "text/html;charset=utf-8";
	
	private Object returnVal;
	
	public DefaultViewResult(Object returnVal){
		this.returnVal = returnVal;
	}

	@Override
	protected void execute() throws IOException {
		HttpServletResponse response = getResponse();
		response.setContentType(CONTENT_TYPE);
		response.getWriter().println(returnVal);
	}

}
