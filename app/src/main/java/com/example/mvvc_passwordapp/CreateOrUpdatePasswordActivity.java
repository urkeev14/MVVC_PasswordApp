package com.example.mvvc_passwordapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvvc_passwordapp.constants.Const;

public class CreateOrUpdatePasswordActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.mvvc_passwordapp.EXTRA_ID";
    public static final String EXTRA_WEBSITE = "com.example.mvvc_passwordapp.EXTRA_WEBSITE";
    public static final String EXTRA_USERNAME = "com.example.mvvc_passwordapp.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD = "com.example.mvvc_passwordapp.EXTRA_PASSWORD";

    private EditText etWebsite;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_password);

        initActivityComponents();
        setActivityMode();

    }

    private void initActivityComponents() {

        etWebsite = findViewById(R.id.etWebsite);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close); //Puts an X in top left corner of menu

    }

    private void setActivityMode() {

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setModeToTxtElements(R.string.update_password);
            etWebsite.setText(intent.getStringExtra(EXTRA_WEBSITE));
            etUsername.setText(intent.getStringExtra(EXTRA_USERNAME));
            etPassword.setText(intent.getStringExtra(EXTRA_PASSWORD));
        } else {
            setModeToTxtElements(R.string.create_password);
        }

    }

    private void setModeToTxtElements(int mode) {
        setTitle(getString(mode));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_create_edit_password, menu);

        return true;
    }

    //This method is like onClick method, but for the menuItems
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuBtnCreate:
                savePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void savePassword() {
        String password = etPassword.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String website = etWebsite.getText().toString().trim();

        if (password.isEmpty() || username.isEmpty() || website.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_PASSWORD, password);
        intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra(EXTRA_WEBSITE, website);

        //If we passed ID from the last activity, then add it to intent so we could bring it back
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();

    }
}