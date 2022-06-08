package com.shop.services.impl;

import com.shop.mapper.AuctionCustomerMapper;
import com.shop.mapper.AuctionMapper;
import com.shop.mapper.AuctionrecordMapper;
import com.shop.pojo.Auction;
import com.shop.pojo.AuctionCustomer;
import com.shop.pojo.AuctionExample;
import com.shop.pojo.Auctionrecord;
import com.shop.services.AuctionService;
import com.shop.tools.AuctionPriceException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionMapper auctionMapper;
    private final AuctionCustomerMapper auctionCustomerMapper;
    private final AuctionrecordMapper auctionrecordMapper;

    public AuctionServiceImpl(AuctionMapper auctionMapper, AuctionCustomerMapper auctionCustomerMapper, AuctionrecordMapper auctionrecordMapper) {
        this.auctionMapper = auctionMapper;
        this.auctionCustomerMapper = auctionCustomerMapper;
        this.auctionrecordMapper = auctionrecordMapper;
    }

    @Override
    public List<Auction> getAuctions(Auction auction) {

        //创建查询对象
        if (auction != null) {
            final AuctionExample auctionExample = new AuctionExample();
            final AuctionExample.Criteria criteria = auctionExample.createCriteria();
            // 竞拍商品的名称
            if (auction.getAuctionname() != null && !"".equals(auction.getAuctionname())) {
//                System.out.println(auction.getAuctionname()+"=============");
                //模糊查询
                criteria.andAuctionnameLike(String.format("%%%s%%", auction.getAuctionname()));
            }

            // 竞拍品描述的查询
            if (auction.getAuctiondesc() != null && !"".equals(auction.getAuctiondesc())) {
//                System.out.println(auction.getAuctiondesc()+"=============");
                // 匹配查询
                criteria.andAuctiondescEqualTo(auction.getAuctiondesc());
            }
            // 大于开始时间
            if (auction.getAuctionstarttime() != null) {
//                System.out.println(auction.getAuctionstarttime() + "=============");
                criteria.andAuctionstarttimeGreaterThan(auction.getAuctionstarttime());
            }
            // 小于结束时间
            if (auction.getAuctionendtime() != null) {
//                System.out.println(auction.getAuctionendtime() + "=============");
                criteria.andAuctionendtimeLessThan(auction.getAuctionendtime());
            }
            // 大于起拍价
            if (auction.getAuctionstartprice() != null) {
//                System.out.println(auction.getAuctionstartprice()+"=============");
                criteria.andAuctionstartpriceGreaterThan(auction.getAuctionstartprice());
            }
            // 设置起拍时间的降序
            auctionExample.setOrderByClause("auctionstarttime desc");
            return auctionMapper.selectByExample(auctionExample);
        } else {

            return auctionMapper.selectByExample(null);
        }
//        拿全部，没有参数
    }

    @Override
    public Auction selectAuctionAndAuctionRecordList(Integer auctionId) {
        return auctionCustomerMapper.selectAuctionAndAuctionRecordList(auctionId);
    }

    @Override
    public void insertAuctionRecord(Auctionrecord auctionrecord) throws AuctionPriceException {
        final Auction auction = auctionCustomerMapper.selectAuctionAndAuctionRecordList(auctionrecord.getAuctionid());
        //判断商品时间是否过期
        // 判断结束时间，不能过期
        if (!auction.getAuctionendtime().after(new Date())) {

            throw new AuctionPriceException("当前竞拍活动已经结束了");
        }

        // 判断价格：
        //没人竞拍过
        if (auction.getAuctionrecodList().size() > 0) {
            // 当前是有竞拍纪录的

            // 取出所有竞拍纪录中的最高价
            Auctionrecord maxRecord = auction.getAuctionrecodList().get(0);

            // compareTo  比较 BigDeimal   0 1  -1
            if (auctionrecord.getAuctionprice().compareTo(maxRecord.getAuctionprice()) < 1) {

                throw new AuctionPriceException("您出的价格不能低于等于最高价");
                //如果商品从未竞价，价格必须高于起拍价
            }

        } else {
            if (auctionrecord.getAuctionprice().compareTo(auction.getAuctionstartprice()) < 1) {

                throw new AuctionPriceException("您出的价格不能低于起拍价");
            }
        }
        auctionrecordMapper.insert(auctionrecord);

    }

    @Override
    public List<AuctionCustomer> selectAuctionendtime() {
        return auctionCustomerMapper.selectAuctionendtime();
    }

    @Override
    public List<Auction> selectAuctionNoendtime() {
        return auctionCustomerMapper.selectAuctionNoendtime();
    }

    @Override
    public void addAuction(Auction auction) {
        auctionMapper.insert(auction);
    }


}
