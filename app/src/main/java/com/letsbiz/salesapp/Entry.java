package com.letsbiz.salesapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Entry {
    public String shopName;
    public String ownerName;
    public String shopCategory;
    public String shopOwnerSug;
    public String userSug;
    public String isInstalled;
    public String isRegistered;
    public String isRated;
    public String checkaCommot;

    public Entry() {}

    public Entry(String shopName, String ownerName, String shopCategory, String shopOwnerSug, String userSug, String isInstalled, String isRegistered, String isRated) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.shopCategory = shopCategory;
        this.shopOwnerSug = shopOwnerSug.isEmpty() ? "None" : shopOwnerSug;
        this.userSug = userSug.isEmpty() ? "None" : userSug;
        this.isInstalled = isInstalled;
        this.isRegistered = isRegistered;
        this.isRated = isRated;
    }

    public String invalidFilds() {
        String res = "";
        if (shopName.isEmpty()) res = "Shop name";
        if(ownerName.isEmpty()) res = (res.isEmpty() ? "" : " and ") + "Owner name";

        return res;
    }

    public boolean setShopName(String shopName) {
        if (shopName.isEmpty()) return false;

        this.shopName = shopName;
        return true;
    }

    public boolean setOwnerName(String ownerName) {
        if(ownerName.isEmpty()) return false;

        this.ownerName = ownerName;
        return true;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public void setShopOwnerSug(String shopOwnerSug) {
        this.shopOwnerSug = shopOwnerSug;
    }

    public void setUserSug(String userSug) {
        this.userSug = userSug;
    }

    public void setIsInstalled(String isInstalled) {
        this.isInstalled = isInstalled;
    }

    public void setIsRegistered(String isRegistered) {
        this.isRegistered = isRegistered;
    }

    public void setIsRated(String isRated) {
        this.isRated = isRated;
    }
}
