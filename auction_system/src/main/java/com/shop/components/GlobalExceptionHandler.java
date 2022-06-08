package com.shop.components;

import com.shop.tools.AuctionPriceException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GlobalExceptionHandler {}
@Component
class GlobalExceptionHandler2 implements HandlerExceptionResolver{

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ex.printStackTrace();
        //        System.out.println("GlobalExceptionHandler2");
//        System.out.println("HandlerExceptionResolver");
//        System.out.println("resolveException");
        final AuctionPriceException auctionPriceException = ex instanceof AuctionPriceException ? ((AuctionPriceException) ex) : null;

        final String exceptionMessage;
        if (auctionPriceException == null) {
            exceptionMessage = "未知异常";
        } else {
            exceptionMessage = auctionPriceException.getMessage();
        }
        final ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("errorMsg", exceptionMessage);
        modelAndView.setViewName("errorPage");
        return modelAndView;
    }
}
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    private ModelAndView test0(
//            HttpServletRequest httpServletRequest,
//            HttpServletResponse httpServletResponse,
//            HttpSession httpSession,
//            Exception exception
//    ) {
//        System.out.println("GlobalExceptionHandler");
//        System.out.println("@ExceptionHandler(Exception.class)");
//        final AuctionPriceException auctionPriceException = exception instanceof AuctionPriceException ? ((AuctionPriceException) exception) : null;
//
//        final String exceptionMessage;
//        if (auctionPriceException == null) {
//            exceptionMessage = "未知异常";
//        } else {
//            exceptionMessage = auctionPriceException.getMessage();
//        }
//        final ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.addObject("errorMsg", exceptionMessage);
//        modelAndView.setViewName("errorPage");
//        return modelAndView;
//    }
//}
