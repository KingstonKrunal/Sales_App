package com.letsbiz.salesapp.model;

public class User {
    private static boolean isAdmin;
    private static String UID;


    public static String getUID() {
        return UID;
    }

    public static void setUID(String UID) {
        User.UID = UID;
    }


    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        User.isAdmin = isAdmin;
    }
}
