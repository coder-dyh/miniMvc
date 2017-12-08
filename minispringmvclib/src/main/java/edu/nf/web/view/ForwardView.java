package edu.nf.web.view;

import edu.nf.web.ViewResult;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 转发视图
 */
public class ForwardView extends ViewResult {

    private String url;

    public ForwardView(String url) {
        this.url = url;
    }

    @Override
    protected void execute() throws IOException, ServletException {
        if (url != null) {
            getRequest().getRequestDispatcher("/" + url.trim()).forward(getRequest(),
                    getResponse());
        }
    }
}
