/*
 *  Create by platform eight on 2020. 6. 10.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.platformeight.coffee.R;
import com.platformeight.coffee.SharedPreference;

import static com.platformeight.coffee.Constant.LOGIN_STATE;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);
        final CheckBox remeber = findViewById(R.id.remember);
        final CheckBox autologin = findViewById(R.id.autologin);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        if (SharedPreference.hasAttribute(this,"REMEMBER")){
            if (SharedPreference.getAttribute(this, "REMEMBER").equals("true")){
                remeber.setChecked(true);
                usernameEditText.setText(SharedPreference.getAttribute(getApplicationContext(),"ID"));
                passwordEditText.setText(SharedPreference.getAttribute(getApplicationContext(),"PASS"));
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        }
        if (SharedPreference.hasAttribute(this,"AUTO")){
            autologin.setChecked(SharedPreference.getAttribute(this, "AUTO").equals("true"));
        }
        autologin.setOnClickListener(v -> {
            if (((CheckBox)v).isChecked()){
                remeber.setChecked(true);
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    //Complete and destroy login activity once successful
                    if (autologin.isChecked()) {
                        SharedPreference.setAttribute(getApplicationContext(),"AUTO","true");
                        SharedPreference.setAttribute(getApplicationContext(),"ID",usernameEditText.getText().toString());
                        SharedPreference.setAttribute(getApplicationContext(),"PASS",passwordEditText.getText().toString());
                    } else SharedPreference.setAttribute(getApplicationContext(),"AUTO","false");
                    if (remeber.isChecked()) {
                        SharedPreference.setAttribute(getApplicationContext(),"REMEMBER","true");
                        SharedPreference.setAttribute(getApplicationContext(),"ID",usernameEditText.getText().toString());
                        SharedPreference.setAttribute(getApplicationContext(),"PASS",passwordEditText.getText().toString());
                    } else {
                        SharedPreference.setAttribute(getApplicationContext(),"REMEMBER","false");
                    }
                    updateUiWithUser(loginResult.getSuccess());
                    finish();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = model.getDisplayName() + getString(R.string.welcome);
        // TODO : initiate successful logged in experience 로그인성공시
        Intent result = new Intent();
        result.putExtra(LOGIN_STATE, true);
        setResult(Activity.RESULT_OK, result);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}