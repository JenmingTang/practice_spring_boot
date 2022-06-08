package com.shop.mapper;

import com.shop.pojo.Auction;
import com.shop.pojo.AuctionCustomer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AuctionCustomerMapper {

   Auction  selectAuctionAndAuctionRecordList(Integer auctionId);


   /**
    *
    * @查询已经结束的拍卖商品
    */
  List<AuctionCustomer> selectAuctionendtime();




  /**查询正在拍卖的商品*/
   List<Auction> selectAuctionNoendtime();

}
