/*
 *  Create by platform eight on 2020. 6. 12.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee.ui.login;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.platformeight.coffee.MemberData;
import com.platformeight.coffee.MyApplication;
import com.platformeight.coffee.R;
import com.platformeight.coffee.servertask.ServerHandle;

import static com.platformeight.coffee.MyApplication.user;

public class RegisterActivity extends AppCompatActivity {
    EditText id;
    EditText pass1;
    EditText pass2;
    EditText name;
    EditText phone;
    Button btn_check;
    Button btn_register;
    int check;
    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialView();
        initialData();
    }

    private void initialView() {
        id = findViewById(R.id.register_Id);
        pass1 = findViewById(R.id.register_password);
        pass2 = findViewById(R.id.register_password2);
        name = findViewById(R.id.register_name);
        phone = findViewById(R.id.register_phone);
        btn_check = findViewById(R.id.register_btn_check);
        btn_register = findViewById(R.id.register_btn_ok);
        registerViewModel = ViewModelProviders.of(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);
    }

    private void initialData() {
        check=-1;
        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                btn_register.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getUsernameError() != null) {
                    id.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    pass1.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getPassword2Error() != null) {
                    pass2.setError(getString(registerFormState.getPassword2Error()));
                }
                if (registerFormState.getNameError() != null) {
                    name.setError(getString(registerFormState.getNameError()));
                }
                if (registerFormState.getPhoneError() != null) {
                    phone.setError(getString(registerFormState.getPhoneError()));
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
                registerViewModel.RegisterDataChanged(id.getText().toString(),
                        pass1.getText().toString(),pass2.getText().toString(),name.getText().toString(), phone.getText().toString());
            }
        };
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ( id.getText().toString().trim().length()>7 ){
                    btn_check.setEnabled(true);
                }else{
                    btn_check.setEnabled(false);
                }
            }
        });
        pass1.addTextChangedListener(afterTextChangedListener);
        pass2.addTextChangedListener(afterTextChangedListener);
        //phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        name.addTextChangedListener(afterTextChangedListener);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("KR")); // API Level 21. Country Code
        phone.addTextChangedListener(afterTextChangedListener);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = new ServerHandle().findID(id.getText().toString());
                if (check>0) {
                    Toast.makeText(RegisterActivity.this, "아이디 중복값이 있습니다.", Toast.LENGTH_SHORT).show();
                    check = -1;
                } else if (check==0){
                    Toast.makeText(RegisterActivity.this, "아이디를 사용해도 됩니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check==-1){
                    Toast.makeText(RegisterActivity.this, "아이디 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (check>0){
                    Toast.makeText(RegisterActivity.this, "아이디 중복값이 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setEmail(id.getText().toString());
                user.setPass(pass1.getText().toString());
                user.setName(name.getText().toString());
                user.setPhone(phone.getText().toString());

                if(new ServerHandle().register(user)){
                    Toast.makeText(RegisterActivity.this, user.getName()+"님 회원등록이 되셨습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this, "회원등록이 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}