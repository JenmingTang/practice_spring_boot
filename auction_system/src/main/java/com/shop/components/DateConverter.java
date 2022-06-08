package com.shop.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String time) {
		/*
		auction = Auction(auctionid=50, auctionname=四手电脑22, auctionstartprice=111.00, auctionupset=111.00, auctionstarttime=Tue Apr 02 16:00:00 CST 2019, auctionendtime=Fri Apr 26 16:00:00 CST 2019, auctionpic=white_under.jpg, auctionpictype=image/jpeg, auctiondesc=aa, auctionrecodList=null)
DateConverter=================
time = 2019-04-26 16:00:00
=================DateConverter
DateConverter=================
time = 2019-04-02 16:00:00
=================DateConverter
		 */
//		System.out.println("DateConverter=================");
//		System.out.println("time = " + time);
//		System.out.println("=================DateConverter");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
//			报错，因为 Auction 的时间为 ""、null
//			e.printStackTrace();
        }
        return null;
    }

}
