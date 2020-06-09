package com.example.mvvc_passwordapp.mvvm.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvc_passwordapp.mvvm.entity.PasswordEntity;

import java.util.List;

@Dao
public interface PasswordDao {

    @Insert
    void insert(PasswordEntity passwordEntity);

    @Update
    void update(PasswordEntity passwordEntity);

    @Delete
    void delete(PasswordEntity passwordEntity);

    @Query("DELETE FROM password_table")
    void deleteAll();

    @Query("SELECT * FROM password_table ORDER BY website DESC")
    LiveData<List<PasswordEntity>> getAllPasswords();

}
