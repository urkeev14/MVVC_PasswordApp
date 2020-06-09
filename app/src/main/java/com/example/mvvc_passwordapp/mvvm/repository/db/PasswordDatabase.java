package com.example.mvvc_passwordapp.mvvm.repository.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mvvc_passwordapp.mvvm.dao.PasswordDao;
import com.example.mvvc_passwordapp.mvvm.entity.PasswordEntity;

@Database(entities = PasswordEntity.class, version = 1)
public abstract class PasswordDatabase extends RoomDatabase {

    private static PasswordDatabase instance;
    public abstract PasswordDao passwordDao();

    public static synchronized PasswordDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PasswordDatabase.class,
                    "password_database").fallbackToDestructiveMigrationFrom().build();
        }
        return instance;
    }

}
