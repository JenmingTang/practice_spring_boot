package com.shop.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shop.pojo.User;
import com.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
// @RequestMapping("/user")
public class UserController {
    private final DefaultKaptcha captchaProducer;
    private final UserService userService;

    public UserController(DefaultKaptcha captchaProducer, UserService userService) {
        this.captchaProducer = captchaProducer;
        this.userService = userService;
    }

    @RequestMapping("/registerUser")
    public String registerUser(User user) {
        userService.registerUser(user);
//        forward:/

        return "login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String valideCode, HttpSession httpSession, String username, String userPassword, Model model) {
        String jumpPageFlag = null;
        final List<User> users = userService.doLogin(username, userPassword);
//        先判空，后别的
        if (users == null || users.isEmpty()) {
            model.addAttribute("errorMsg", "username or password error");
        } else {
            httpSession.setAttribute("user", users.get(0));

            jumpPageFlag = "index";

        }
        final Object vrifyCode = httpSession.getAttribute("vrifyCode");

        if (!valideCode.equals(vrifyCode)) {
            model.addAttribute("errorMsg", "verification code error");
//            System.out.println("==============================");
//            System.out.println("vrifyCode = " + vrifyCode);
//            System.out.println("valideCode = " + valideCode);
//            System.out.println("==============================");
            jumpPageFlag = "login";
        }
//        System.out.println("jumpPageFlag = " + jumpPageFlag);

//        return jumpPageFlag;
        //写死，直接登进去
        //写死，直接登进去
        //写死，直接登进去
//        都是 0 权限
//        final User user = new User();
//        user.setUserisadmin(1);
//        user.setUserid(5);
//        user.setUsername("tang_test");
//        httpSession.setAttribute("user",user);
        //有数据的 index
//        jumpPageFlag = "getAuctions";
        return "forward:/getAuctions";

    }

    /**
     * 获取验证码
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
