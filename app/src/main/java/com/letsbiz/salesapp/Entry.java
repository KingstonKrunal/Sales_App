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
    public float ratings;

    public Entry() {}

    public Entry(String shopName, String ownerName, String shopCategory, String shopOwnerSug, String userSug, String isInstalled, String isRegistered, float ratings) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.shopCategory = shopCategory;
        this.shopOwnerSug = shopOwnerSug.isEmpty() ? "None" : shopOwnerSug;
        this.userSug = userSug.isEmpty() ? "None" : userSug;
        this.isInstalled = isInstalled;
        this.isRegistered = isRegistered;
        this.ratings = ratings;
    }

    public String invalidFields() {
        String res = "";
        if (shopName != null && shopName.isEmpty()) res = "Shop name";
        if(ownerName != null && ownerName.isEmpty()) res = (res.isEmpty() ? "" : " and ") + "Owner name";

        return res;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }
}
