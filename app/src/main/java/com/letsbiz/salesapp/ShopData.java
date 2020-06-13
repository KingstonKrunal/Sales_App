package com.letsbiz.salesapp;

public class ShopData {
    private String shopName, shopOwnerName, shopCategory, theirSugg, yourSugg, downloadQue, regQue,
            rateQue;

    public ShopData(String shopName, String shopOwnerName, String shopCategory, String theirSugg,
                    String yourSugg, String downloadQue, String regQue, String rateQue){
        this.shopName = shopName;
        this.shopOwnerName = shopOwnerName;
        this.shopCategory = shopCategory;
        this.theirSugg = theirSugg;
        this.yourSugg = yourSugg;
        this.downloadQue = downloadQue;
        this.regQue = regQue;
        this.rateQue = rateQue;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopOwnerName() {
        return shopOwnerName;
    }

    public void setShopOwnerName(String shopOwnerName) {
        this.shopOwnerName = shopOwnerName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getTheirSugg() {
        return theirSugg;
    }

    public void setTheirSugg(String theirSugg) {
        this.theirSugg = theirSugg;
    }

    public String getYourSugg() {
        return yourSugg;
    }

    public void setYourSugg(String yourSugg) {
        this.yourSugg = yourSugg;
    }

    public String getDownloadQue() {
        return downloadQue;
    }

    public void setDownloadQue(String downloadQue) {
        this.downloadQue = downloadQue;
    }

    public String getRegQue() {
        return regQue;
    }

    public void setRegQue(String regQue) {
        this.regQue = regQue;
    }

    public String getRateQue() {
        return rateQue;
    }

    public void setRateQue(String rateQue) {
        this.rateQue = rateQue;
    }
}
