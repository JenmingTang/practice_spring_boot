package com;

import com.shop.AuctionSystemApp;
import com.shop.mapper.AuctionCustomerMapper;
import com.shop.pojo.Auction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(classes = {AuctionSystemApp.class})
public class App {
    @Autowired
    private AuctionCustomerMapper auctionCustomerMapper;
    @Test
    public void testString(){
        final Auction auction = auctionCustomerMapper.selectAuctionAndAuctionRecordList(43);
        System.out.println("======================================");
        System.out.println("auction.toString() = " + auction.toString());
        System.out.println("======================================");
    }
}
