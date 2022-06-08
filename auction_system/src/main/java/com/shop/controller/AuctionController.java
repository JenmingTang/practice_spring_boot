package com.shop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.AuctionMapper;
import com.shop.pojo.*;
import com.shop.services.AuctionService;
import net.sf.jsqlparser.schema.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class AuctionController {
    //    ============= 偷懒
    @Autowired
    private AuctionMapper auctionMapper;
    //    =============
    private final static int PAGE_SIZE = 6;
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @RequestMapping("/getAuctions")
//    @ModelAttribute("condition") Auction condition_tang 自己 new 一个放到 request 域
//    Auction condition_tang Auction auction 都能收到缺少字段的对象
    //现在一个 form 做 分页、对象 ，可以做多种操作 666
    public String getAuctions(@ModelAttribute("condition") Auction auction, @RequestParam(required = false, defaultValue = "1", name = "pageNumber") int pageNumber, Model model) {
//        System.out.println("==========================");
//        System.out.println("condition_tang.toString() = " + condition_tang.toString());
//        System.out.println("==========================");
//        System.out.println("auction.toString() = " + auction.toString());
//        System.out.println("==========================");
//        只出来个字符串，要加 ${} 动态吗，要
//        th:text="auction.auctionstarttime"
        PageHelper.startPage(pageNumber, PAGE_SIZE);

//        final List<Auction> auctions;
//        if (auction.getAuctionid() == null) {
//            auctions = auctionService.getAuctions(null);
//            System.out.println("getAuctions(null)");
//            auctions.forEach(System.out::println);
//        } else {
//
//            auctions = auctionService.getAuctions(auction);
//            System.out.println("getAuctions(auction)");
//            auctions.forEach(System.out::println);
//        }
        final List<Auction> auctions = auctionService.getAuctions(auction);
        final PageInfo<Auction> auctionPageInfo = new PageInfo<>(auctions);

        model.addAttribute("auctionPageInfo", auctionPageInfo);
        return "index";
    }
//    @RequestMapping("/pagination")
//    public String pagination(HttpSession httpSession, Model model, @RequestParam(required = false, defaultValue = "1", name = "pageNumber") int pageNumber){
//        //        我自己的分页操作
//        /**
//         * 【记住：必须在mapper接口中的方法执行之前设置该分页信息】
//         * PageHelper.startPage(pageNo,pageSize) 只对其后的第一个查询有效
//         */
//        PageHelper.startPage(pageNumber, PAGE_SIZE);
//        final List<Auction> auctions = auctionService.getAuctions(null);
//        final PageInfo<Auction> auctionPageInfo = new PageInfo<>(auctions);
//        model.addAttribute("auctionPageInfo", auctionPageInfo);
//        return "index";
//
//    }

    //竞拍跳转
    @RequestMapping("/findAuctionDetial/{auctionid}")
    public String findAuctionDetialBy(@PathVariable int auctionid, Model model) {
        final Auction auction = auctionService.selectAuctionAndAuctionRecordList(auctionid);
        model.addAttribute("auctionDetail", auction);
        return "auctionDetail";
    }

    @RequestMapping("/auctionDelete/{auctionId}")
    public String auctionDelete(@PathVariable int auctionId) {
        auctionMapper.deleteByPrimaryKey(auctionId);
        return "redirect:/getAuctions";
    }

    @RequestMapping("/auctionChange/{auctionId}")
    public String auctionChange(@PathVariable int auctionId, Model model) {
        final Auction auction = auctionMapper.selectByPrimaryKey(auctionId);
        model.addAttribute("auction", auction);
        return "addAuction";
    }

    //一直报，因为前面跳到这啥都没做，return null，
    //Error resolving template [saveAuctionRecord], template might not exist or might not be accessible
    //路径变成这个，后出错，post 进来的
    //http://localhost:8086/saveAuctionRecord
    @RequestMapping("/saveAuctionRecord")
    public String saveAuctionRecord(Auctionrecord auctionrecord, HttpSession httpSession) {
        auctionrecord.setAuctiontime(new Date());
        final User user = (User) httpSession.getAttribute("user");
//        auctionrecord.setUser(user);
        auctionrecord.setUserid(user.getUserid());
        auctionService.insertAuctionRecord(auctionrecord);
        return "redirect:/findAuctionDetial/" + auctionrecord.getAuctionid();
    }


    @RequestMapping("/toAuctionResult")
    public ModelAndView toAuctionResult(ModelAndView modelAndView) {
        final List<AuctionCustomer> auctionCustomers = auctionService.selectAuctionendtime();
        final List<Auction> auctions = auctionService.selectAuctionNoendtime();
        modelAndView.addObject("endtimeList", auctionCustomers);
        modelAndView.addObject("noendtimeList", auctions);
        modelAndView.setViewName("auctionResult");
        return modelAndView;
    }


    @RequestMapping("/addAuctionTang")
    public String toAuctionPage(@RequestParam("pic") MultipartFile multipartFile, Auction auction) throws IOException {

        if (auction.getAuctionpic() == null) {
            auction.setAuctionpic("");
            //他奶奶滴，表里 varchar(20)
            // MysqlDataTruncation: Data truncation: Data too long for column 'auctionPicType' at row 1
            auction.setAuctionpictype("");
        }
//        =============
        //        System.out.println("auction.getAuctionid() = " + auction.getAuctionid());
//        不存在上传图片文件时，空的串 originalFilename =
        if (!Objects.equals(multipartFile.getOriginalFilename(), "")) {

            final String originalFilename = multipartFile.getOriginalFilename();
            auction.setAuctionpic(originalFilename);
            final String contentType = multipartFile.getContentType();
            auction.setAuctionpictype(contentType);
//        System.out.println("==========================");
//        originalFilename = white_under.jpg
//        contentType = image/jpeg
//        不存在上传图片文件时，空的串 originalFilename =
//        System.out.println("originalFilename = " + originalFilename);
//        System.out.println("contentType = " + contentType);
//        System.out.println("==========================");
            //在 disk new file，注意 windows 下权限
            final File file = new File(String.format("D:\\temporary\\%s", originalFilename));
            final boolean newFile = file.createNewFile();
            multipartFile.transferTo(file);
        }
        auctionMapper.insert(auction);

        return "redirect:/getAuctions";
    }

    @RequestMapping("/publishAuctions")
//    MultipartFile null indexed

    public ModelAndView publishAuctions(@RequestParam("pic") MultipartFile multipartFile, Auction auction, ModelAndView modelAndView) throws IOException {
//============
        /**
         * auctiondesc=tangdada 有读到 <textarea> value 到为啥 th:value="" 赋值使不显示？？
         * 解决：为 th:text=""
         */
//        =============
//        Column 'auctionPic' cannot be null
//        mappings\AuctionMapper.xml
        if (auction.getAuctionpic() == null) {
            auction.setAuctionpic("");
            //他奶奶滴，表里 varchar(20)
            // MysqlDataTruncation: Data truncation: Data too long for column 'auctionPicType' at row 1
            auction.setAuctionpictype("");
        }
//        =============
        //        System.out.println("auction.getAuctionid() = " + auction.getAuctionid());
//        不存在上传图片文件时，空的串 originalFilename =
        if (!Objects.equals(multipartFile.getOriginalFilename(), "")) {

            final String originalFilename = multipartFile.getOriginalFilename();
            auction.setAuctionpic(originalFilename);
            final String contentType = multipartFile.getContentType();
            auction.setAuctionpictype(contentType);
//        System.out.println("==========================");
//        originalFilename = white_under.jpg
//        contentType = image/jpeg
//        不存在上传图片文件时，空的串 originalFilename =
//        System.out.println("originalFilename = " + originalFilename);
//        System.out.println("contentType = " + contentType);
//        System.out.println("==========================");
            //在 disk new file，注意 windows 下权限
            final File file = new File(String.format("D:\\temporary\\%s", originalFilename));
            final boolean newFile = file.createNewFile();
            multipartFile.transferTo(file);
        }

        //        ===============现在是修改 auction，
        //duplicate insert id 会报错，
        // 所以先删后插。且指定 主键 id 的话，可以替代原先删除的主键 id
        // 所以先删后插。且指定 主键 id 的话，可以替代原先删除的主键 id
        // 所以先删后插。且指定 主键 id 的话，可以替代原先删除的主键 id
        //insert into people (people.id,people.`name`,people.remark) VALUES (11,'tangadad','tang');

        if (auction.getAuctionid() != null) {
//            为修改，client 用表单隐藏 input 域实现
//            System.out.println("为修改，client 用表单隐藏 input 域实现");
//            ===========================
            //我在数据库软件 navicat 把 auctionrecord 表的 auction 表的 auctionId 外键约束删掉了
            //我在数据库软件 navicat 把 auctionrecord 表的 auction 表的 auctionId 外键约束删掉了
            //我在数据库软件 navicat 把 auctionrecord 表的 auction 表的 auctionId 外键约束删掉了
//            先删后插
//            SQLIntegrityConstraintViolationException:
//            Cannot delete or update a parent row: a foreign key constraint fails
//
            auctionMapper.deleteByPrimaryKey(auction.getAuctionid());
//            ===========================
//        ===============
        }
//        System.out.println("auction = " + auction);
        auctionService.addAuction(auction);

        modelAndView.setViewName("redirect:/getAuctions");
        return modelAndView;
    }
}
