package com.example.mohamdkazem.advancetodolist;


public class setUsersId {
    static Long userId;


    public static void setUserId(Long userId) {
        setUsersId.userId = userId;
    }

    public static Long getUserId() {
        return userId;
    }
}
