package com.example.exceptionHandle;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class MyHandler {
    @ExceptionHandler({MaxUploadSizeExceededException.class, FileSizeLimitExceededException.class})
//    这里 参数 不能直接给 ModelAndView
    private ModelAndView myHandler0(
            MaxUploadSizeExceededException maxUploadSizeExceededException,
            FileSizeLimitExceededException fileSizeLimitExceededException,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            HttpSession httpSession
    ) {
        System.out.println("\"myHandler0\" = " + "myHandler0");
        System.out.println("\"myHandler0\" = " + "myHandler0");
        System.out.println("\"myHandler0\" = " + "myHandler0");
        String err_msg = "";
        if (maxUploadSizeExceededException != null) {
            err_msg = "maxUploadSizeExceededException";
        } else {

            err_msg = "fileSizeLimitExceededException";

        }
        System.out.println("err_msg = " + err_msg);
        System.out.println("err_msg = " + err_msg);
        System.out.println("err_msg = " + err_msg);
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", err_msg);
        modelAndView.setViewName("error2");
        return modelAndView;
    }
}
