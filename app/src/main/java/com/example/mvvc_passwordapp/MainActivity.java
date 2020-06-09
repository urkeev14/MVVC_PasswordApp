package com.example.mvvc_passwordapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvc_passwordapp.adapter.RecyclerViewPasswordAdapter;
import com.example.mvvc_passwordapp.constants.Const;
import com.example.mvvc_passwordapp.mvvm.entity.PasswordEntity;
import com.example.mvvc_passwordapp.mvvm.viewmodel.PasswordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CREATE_PASSWORD_MODE = 1;
    public static final int UPDATE_PASSWORD_MODE = 2;

    private FloatingActionButton btnCreatePassword;
    private RecyclerView rvPassword;
    private RecyclerViewPasswordAdapter adapter;
    private PasswordViewModel passwordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeActivityElements();
        observePasswordsChange();

        configureOnAdapterClickListener();
        configureOnCardSwipeAction();
    }

    private void configureOnCardSwipeAction() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                PasswordEntity password = adapter.getPasswordAt(position);
                passwordViewModel.delete(password);

                Toast.makeText(MainActivity.this, "Password deleted...", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvPassword);
    }

    private void configureOnAdapterClickListener() {
        adapter.setOnItemClickListener(new RecyclerViewPasswordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PasswordEntity passwordEntity) {
                Intent intent = new Intent(MainActivity.this, CreateOrUpdatePasswordActivity.class);

                intent.putExtra(CreateOrUpdatePasswordActivity.EXTRA_ID, passwordEntity.getId());
                intent.putExtra(CreateOrUpdatePasswordActivity.EXTRA_USERNAME, passwordEntity.getUsername());
                intent.putExtra(CreateOrUpdatePasswordActivity.EXTRA_PASSWORD, passwordEntity.getPassword());
                intent.putExtra(CreateOrUpdatePasswordActivity.EXTRA_WEBSITE, passwordEntity.getWebsite());

                startActivityForResult(intent, UPDATE_PASSWORD_MODE);
            }
        });
    }

    private void initializeActivityElements() {
        btnCreatePassword = findViewById(R.id.btnCreatePassword);
        btnCreatePassword.setOnClickListener(this);

        rvPassword = findViewById(R.id.rvPassword);
        rvPassword.setLayoutManager(new LinearLayoutManager(this));
        rvPassword.setHasFixedSize(true);

        adapter = new RecyclerViewPasswordAdapter();
        rvPassword.setAdapter(adapter);

    }

    private void observePasswordsChange() {
        passwordViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
        passwordViewModel.getAllPasswords().observe(this, new Observer<List<PasswordEntity>>() {
            @Override
            public void onChanged(List<PasswordEntity> passwordEntities) {
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                adapter.setPasswords(passwordEntities);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreatePassword:
                onBtnAddPassword();
                break;
            default:
                break;
        }
    }

    private void onBtnAddPassword() {
        Intent intent = new Intent(MainActivity.this, CreateOrUpdatePasswordActivity.class);
        startActivityForResult(intent, Const.CREATE_PASSWORD_MODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CREATE_PASSWORD_MODE:
                    createPassword(data);
                    break;
                case UPDATE_PASSWORD_MODE:
                    updatePassword(data);
                    break;
                default:
                    break;
            }
        }
    }

    private void createPassword(Intent data) {
        PasswordEntity newPassword = generatePasswordFromData(data);

        passwordViewModel.insert(newPassword);
    }

    private void updatePassword(Intent data) {
        PasswordEntity updatedPassword = generatePasswordFromData(data);

        int id = data.getIntExtra(CreateOrUpdatePasswordActivity.EXTRA_ID, -1);
        updatedPassword.setId(id);

        passwordViewModel.update(updatedPassword);
    }

    private PasswordEntity generatePasswordFromData(Intent data) {
        String password = data.getStringExtra(CreateOrUpdatePasswordActivity.EXTRA_PASSWORD);
        String username = data.getStringExtra(CreateOrUpdatePasswordActivity.EXTRA_USERNAME);
        String website = data.getStringExtra(CreateOrUpdatePasswordActivity.EXTRA_WEBSITE);

        return new PasswordEntity(password, website, username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miDeleteAllPasswords:
                passwordViewModel.deleteAll();
                Toast.makeText(this, "All passwords deleted...", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}