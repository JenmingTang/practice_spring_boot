package com.shop.tools;

public class AuctionPriceException extends RuntimeException{
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuctionPriceException(String message) {

        this.message = message;
    }

    private String message;
}
