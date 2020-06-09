package com.example.mvvc_passwordapp.mvvm.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "password_table")
public class PasswordEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String password;
    private String website;
    private String username;

    public PasswordEntity(String password, String website, String username) {
        this.password = password;
        this.website = website;
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getWebsite() {
        return website;
    }
    public String getUsername() {
        return username;
    }
}
