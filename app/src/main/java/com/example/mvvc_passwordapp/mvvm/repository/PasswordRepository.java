package com.example.mvvc_passwordapp.mvvm.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mvvc_passwordapp.mvvm.dao.PasswordDao;
import com.example.mvvc_passwordapp.mvvm.entity.PasswordEntity;
import com.example.mvvc_passwordapp.mvvm.repository.db.PasswordDatabase;

import java.util.List;

public class PasswordRepository {

    private PasswordDao passwordDao;
    private LiveData<List<PasswordEntity>> allPasswords;

    public PasswordRepository(Application application) {
        PasswordDatabase db = PasswordDatabase.getInstance(application);
        passwordDao = db.passwordDao();
        allPasswords = passwordDao.getAllPasswords();
    }

    public void insert(PasswordEntity passwordEntity) {
        new InsertPasswordAsyncTask(passwordDao).execute(passwordEntity);
    }

    public void update(PasswordEntity passwordEntity) {
        new UpdatePasswordAsyncTask(passwordDao).execute(passwordEntity);
    }

    public void delete(PasswordEntity passwordEntity) {
        new DeletePasswordAsyncTask(passwordDao).execute(passwordEntity);
    }

    public void deleteAll() {
        new DeleteAllPasswordsAsyncTask(passwordDao).execute();
    }

    public LiveData<List<PasswordEntity>> getAllPasswords() {
        return allPasswords;
    }

    /*Because we have to do CRUD operations on backgroud thread (otherwise, our app would crash), we implement classes below*/

    private static class InsertPasswordAsyncTask extends AsyncTask<PasswordEntity, Void, Void> {

        private PasswordDao dao;

        private InsertPasswordAsyncTask(PasswordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PasswordEntity... passwordEntities) {
            dao.insert(passwordEntities[0]);
            return null;
        }
    }

    private static class UpdatePasswordAsyncTask extends AsyncTask<PasswordEntity, Void, Void> {

        private PasswordDao dao;

        private UpdatePasswordAsyncTask(PasswordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PasswordEntity... passwordEntities) {
            dao.update(passwordEntities[0]);
            return null;
        }
    }

    private static class DeletePasswordAsyncTask extends AsyncTask<PasswordEntity, Void, Void> {

        private PasswordDao dao;

        private DeletePasswordAsyncTask(PasswordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PasswordEntity... passwordEntities) {
            dao.delete(passwordEntities[0]);
            return null;
        }
    }

    private static class DeleteAllPasswordsAsyncTask extends AsyncTask<Void, Void, Void> {

        private PasswordDao dao;

        private DeleteAllPasswordsAsyncTask(PasswordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }


}
