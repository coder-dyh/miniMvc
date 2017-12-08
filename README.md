#ministruts2




#cj：项目简要说明
  目的：  此mini struts库，是对struts2 的一个极度简化
        仅仅只是做教学用途
   项目说明：
   ministrutslib，是一个库项目
   ministrutsweb是一个用来测试ministrutslib的web项目
   

#整个流程如下：
##1.StrutsPrepareAndExecuteFilter是一个Servlet过滤器
    init方法：读取classpath下的struts.xml文件
        得到所有的拦截器配置
        得到action访问后缀
        得到所有的Action配置信息
     doFilter方法：
        不以action结尾，继续正常的servlet流程
        得到本次请求的Action信息
        创建ActionInvocation（同时创建了ActionContext对象）。并调用invoke方法
        依据invoke方法返回的字符串值，进行简单的请求转发，并没有像struts2那样有一套Result的处理体系。
        从Threadlocal中移除ActionContext对象

##2.ActionInvocation
    创建ActionInvocation实例
        解析出所有的拦截器
        调用拦截器的init方法
        创建Action类实例
     调用invoke方法：
        依次调用拦截器
        拦截器无返回值就调用Action类的方法。
        最终返回一个字符串
        
##3.ActionContext
    创建ActionContext实例时：
        实例化一个Map<String,Object>的上下文对象（context）
        把request，response，session，application，parameters对象方法放到context中
        创建ValueStack对象
        把Action对象本身放入到ValueStack的栈顶
        把ValueStack对象通过request.setAttribute方法放入到请求作用域中
        最后把值栈对象放入到context对象中
        
##4.ParametersInterceptor
    执行intercept方法时（此方法有参数类型为ActionInvocation）：
        利用ActionInvocation的getActionContext方法得到ActionContext对象
        利用ActionContext对象得到ValueStack对象
        利用ValueStack对象得到Action类的对象
        给Action对象通过BeanUtils.populate给Action的成员赋值

