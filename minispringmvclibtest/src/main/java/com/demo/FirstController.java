package com.demo;

import edu.nf.web.ActionContext;
import edu.nf.web.FrameworkServlet;
import edu.nf.web.ViewResult;
import edu.nf.web.annotation.RequestMapping;
import edu.nf.web.view.ForwardView;

import javax.servlet.http.HttpServletRequest;

public class FirstController {

    @RequestMapping("/first")
    public  String first(){

        return "hello mini spring mvc";
    }

    @RequestMapping("/first2")
    public ViewResult first2(){
        ActionContext actionContext = ActionContext.getContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(FrameworkServlet.REQUEST);
        request.setAttribute("msg","haha");
        return new ForwardView("index.jsp");
    }

    //测试模型绑定：在pom文件给compiler插件设置了-parameters才能生效
    @RequestMapping("/first3")
    public String first3(String name,int id){
        System.out.println("----"  + name + "  " + id);
        return "hello first3";
    }
}
