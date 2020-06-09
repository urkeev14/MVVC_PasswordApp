package com.example.mvvc_passwordapp.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvc_passwordapp.mvvm.entity.PasswordEntity;
import com.example.mvvc_passwordapp.mvvm.repository.PasswordRepository;

import java.util.List;

public class PasswordViewModel extends AndroidViewModel {

    private PasswordRepository passwordRepository;
    private LiveData<List<PasswordEntity>> allPasswords;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        passwordRepository = new PasswordRepository(application);
        allPasswords = passwordRepository.getAllPasswords();
    }



    public void insert(PasswordEntity passwordEntity) {
        passwordRepository.insert(passwordEntity);
    }
    public void update(PasswordEntity passwordEntity) {
        passwordRepository.update(passwordEntity);
    }
    public void delete(PasswordEntity passwordEntity) {
        passwordRepository.delete(passwordEntity);
    }
    public void deleteAll() {
        passwordRepository.deleteAll();
    }
    public LiveData<List<PasswordEntity>> getAllPasswords() {
        return allPasswords;
    }

}
