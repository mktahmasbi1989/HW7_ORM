package com.example.mohamdkazem.advancetodolist.Model;

import org.greenrobot.greendao.annotation.Entity;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;


@Entity
public class Users {
    private String name;
    private String password;
    private String email;


    @Id(autoincrement = true)
    private Long userId;



    @Generated(hash = 268480517)
    public Users(String name, String password, String email, Long userId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.userId = userId;
    }
    @Keep
    public Users(String userName, String passWord, String email) {
        this.name=userName;
        this.password=passWord;
        this.email=email;
    }
    @Generated(hash = 2146996206)
    public Users() {
    }


    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
