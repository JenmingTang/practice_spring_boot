package com.shop.services;

import com.shop.pojo.Auction;
import com.shop.pojo.AuctionCustomer;
import com.shop.pojo.Auctionrecord;
import com.shop.tools.AuctionPriceException;

import java.util.List;

public interface AuctionService {
    List<Auction> getAuctions(Auction auction);
    Auction  selectAuctionAndAuctionRecordList(Integer auctionId);

    void insertAuctionRecord(Auctionrecord auctionrecord) throws AuctionPriceException;


    /**
     *
     * @查询已经结束的拍卖商品
     */
    List<AuctionCustomer> selectAuctionendtime();




    /**查询正在拍卖的商品*/
    List<Auction> selectAuctionNoendtime();

    void addAuction(Auction auction);
}
