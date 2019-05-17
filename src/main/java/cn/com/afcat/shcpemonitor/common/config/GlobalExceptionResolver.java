package cn.com.afcat.shcpemonitor.common.config;

import cn.com.afcat.shcpemonitor.common.json.AjaxJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     * 对异常信息进行统一处理，区分异步和同步请求，分别处理
     */
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("=============exception===");
        boolean isajax = isAjax(request,response);
        //Throwable deepestException = deepestException(ex);
        return processException(request, response, handler, ex, isajax);
    }


    /**
     * 判断当前请求是否为异步请求.
     */
    private boolean isAjax(HttpServletRequest request, HttpServletResponse response){
        return !StringUtils.isEmpty(request.getHeader("X-Requested-With"));
    }

    /**
     * 处理异常.
     * @param request
     * @param response
     * @param handler
     * @param isajax
     * @return
     */
    private ModelAndView processException(HttpServletRequest request,
                                          HttpServletResponse response, Object handler,
                                          Throwable ex, boolean isajax) {

        //步骤一、异常信息记录到日志文件中.

        //步骤二、异常信息记录截取前50字符写入数据库中.

        //步骤三、分普通请求和ajax请求分别处理.
        if(isajax){
            return processAjax(request,response,handler,ex);
        }else{
            return processNotAjax(request, response, handler, ex);
        }
    }

    /**
     * ajax异常处理并返回.
     * @param request
     * @param response
     * @param handler
     * @param deepestException
     * @return
     */
    private ModelAndView processAjax(HttpServletRequest request,
                                     HttpServletResponse response, Object handler,
                                     Throwable deepestException){
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try {
            PrintWriter pw = response.getWriter();
            AjaxJson rs = new AjaxJson();
            rs.setSuccess(false);
            rs.setMsg(deepestException.getMessage());
            pw.write(rs.getJsonStr());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 普通页面异常处理并返回.
     * @param request
     * @param response
     * @param handler
     * @return
     */
    private ModelAndView processNotAjax(HttpServletRequest request,
                                        HttpServletResponse response, Object handler, Throwable ex) {

        String exceptionMessage =ex.getMessage();// ;
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("exceptionMessage", exceptionMessage);
        model.put("exceptionStackTrace", getThrowableMessage(ex));
        model.put("ex", ex);
        return new ModelAndView("error", model);
    }

    /**
     * 返回错误信息字符串
     *
     * @param ex
     *            Exception
     * @return 错误信息字符串
     */
    public String getThrowableMessage(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

}
