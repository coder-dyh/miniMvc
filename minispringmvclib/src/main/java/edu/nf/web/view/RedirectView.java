package edu.nf.web.view;

import edu.nf.web.ActionContext;
import edu.nf.web.ViewResult;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 重定向视图
 */
public class RedirectView extends ViewResult {

    private String url;

    public RedirectView(String url) {
        this.url = url;
    }

    @Override
    protected void execute() throws IOException {
        if (url != null) {
            getResponse().sendRedirect(
                    ActionContext.getContext().getContextPath() + "/"
                            + url.trim());
        }

    }
}
