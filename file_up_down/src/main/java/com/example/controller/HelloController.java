package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    private String hello() {
        return "error";
    }

    @RequestMapping("/tangdada")
    private String tangdada() {
        return "tangdada";
    }


    @RequestMapping("/file_up")
    @ResponseBody
    private String file_up(@RequestParam("file_up") MultipartFile[] multipartFiles, HttpServletRequest request) {
        System.out.println("\"file_up\" = " + "file_up");
        System.out.println("\"file_up\" = " + "file_up");
        System.out.println("\"file_up\" = " + "file_up");
        //设置当前日期
        String uploaddate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //设置文件上传保存文件路径：保存在项目运行目录下的uploadFile文件夹+当前日期
        String savepath = request.getSession().getServletContext().getRealPath("/uploadFile/") + uploaddate;
        //创建文件夹,当文件夹不存在时，创建文件夹
        File folder = new File(savepath);

        if (!folder.isDirectory()) {
            final boolean mkdir = folder.mkdirs();
            System.out.println("创建文件夹");
        }
        //建立一个listmap的返回参数
        List<Map<String, Object>> listmap = new ArrayList<>();
        //建立一个循环分别接收多文件
        for (MultipartFile file : multipartFiles) {
            //重命名上传的文件,为避免重复,我们使用UUID对文件分别进行命名
            String oldname = file.getOriginalFilename();//getOriginalFilename()获取文件名带后缀
            //UUID去掉中间的"-",并将原文件后缀名加入新文件

            assert oldname != null;
            String newname = UUID.randomUUID().toString().replace("-", "")
                    + oldname.substring(oldname.lastIndexOf("."));
            //建立每一个文件上传的返回参数
            Map<String, Object> map = new HashMap<>();
            //文件保存操作
            try {
//                创建文件
                final File created_file = new File(folder, newname);
                final boolean newFile = created_file.createNewFile();
                System.out.println("newFile = " + newFile);
                System.out.println("newFile = " + newFile);
                System.out.println("newFile = " + newFile);
//                java.io.IOException: java.io.FileNotFoundException: C:\\Users\\tang\\AppData\\Local\\Temp\\tomcat-docbase.10000.7860197162197839758\\uploadFile\\2022-05-26\\857ef99740a64a458fb4ba2974b01991.jpeg
//                uploadFile\\2022-05-26\\857ef99740a64a458fb4ba2974b01991.jpeg 开始没有
                file.transferTo(created_file);
                //建立新文件路径,在前端可以直接访问如http://localhost:8080/uploadFile/2021-07-16/新文件名(带后缀)
                String filepath = request.getScheme() + "://" + request.getServerName() + ":" +
                        request.getServerPort() + "/uploadFile/" + uploaddate + "/" + newname;
                //写入返回参数
                map.put("[oldname]", oldname);
                map.put("[newname]", newname);
                map.put("[filepath]", filepath);
                map.put("[result]", "成功!");
                listmap.add(map);
            } catch (IOException ex) {
                //操作失败报错并写入返回参数
                ex.printStackTrace();
                map.put("[oldname]", oldname);
                map.put("[newname]", newname);
                map.put("[filepath]", "");
                map.put("[result]", "失败!");
            }
        }
        //将执行结果返回
        return listmap.toString();
    }
}
