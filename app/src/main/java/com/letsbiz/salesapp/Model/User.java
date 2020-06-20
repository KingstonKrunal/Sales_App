package com.letsbiz.salesapp.Model;

public class User {
    private static boolean isAdmin;

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        User.isAdmin = isAdmin;
    }
}
