package edu.web.view;

import edu.web.ActionContext;
import edu.web.ViewResult;

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
