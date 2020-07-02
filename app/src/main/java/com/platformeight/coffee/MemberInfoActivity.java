/*
 *  Create by platform eight on 2020. 7. 1.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.platformeight.coffee.servertask.ServerHandle;

import static com.platformeight.coffee.MyApplication.user;

public class MemberInfoActivity extends AppCompatActivity {

    Context context;

    private Button btn_modify;
    private Button btn_pass;

    private TextView password1;
    private TextView password2;
    private TextView password3;
    private TextView user_name;
    private TextView user_phone;
    private TextView user_id;

    private boolean visi = false;
    private boolean isValid = false;

    private LinearLayout layout_password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        InitializeView();
        InitializeData();
    }
    public void loginDataChanged(String password1, String password2) {
        if (!password1.equals(password2)) {
            password3.setError("일치하지 않습니다.");
            isValid=false;
        } else {
            password3.setError(null);
            isValid=true;
        }
    }

    private void InitializeData() {
        context = MemberInfoActivity.this;
        user_id.setText(user.getEmail());
        user_name.setText(user.getName());
        user_phone.setText(user.getPhone());
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
                loginDataChanged(password2.getText().toString(),
                        password3.getText().toString());
            }
        };
        user_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("KR")); // API Level 21. Country Code
        password2.addTextChangedListener(afterTextChangedListener);
        password3.addTextChangedListener(afterTextChangedListener);

        btn_pass.setOnClickListener(view -> {
            if(visi){
                password2.setText("");
                password3.setText("");
                layout_password2.setVisibility(View.GONE);
                isValid=false;
            }else{
                layout_password2.setVisibility(View.VISIBLE);
            }
            visi=!visi;
        });
        btn_modify.setOnClickListener(view -> {
            //TODO::서버 회원정보 변경

            /*기존 비밀번호 확인 시작*/
            int check = new ServerHandle().checkPassword(user.getNo(), password1.getText().toString());
            if (check==1){
                /*새 비밀번호 확인 시작*/
                if (visi){
                    Log.d("", "InitializeData: valid "+isValid+" equal "+password2.getText().toString().equals(password3.getText().toString()));
                    if (!isValid) {
                        Toast.makeText(context, "새 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user.setPass(password2.getText().toString());
                }
                Log.d("", "InitializeData: vis "+ visi);
                /*새 비밀번호 확인 끝*/
                user.setName(user_name.getText().toString());
                user.setPhone(user_phone.getText().toString());
                Toast.makeText(context, new ServerHandle().setMember(visi), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
            /*기존 비밀번호 확인 끝*/
        });
    }

    private void InitializeView() {
        user_id = (TextView)findViewById(R.id.user_Id);
        password1 = (TextView)findViewById(R.id.password_modify);
        user_name = (TextView)findViewById(R.id.user_name_modify);
        user_phone = (TextView)findViewById(R.id.user_phone_modify);
        layout_password2 = (LinearLayout)findViewById(R.id.layout_password2);
        password2 = (TextView)findViewById(R.id.password2_modify);
        password3 = (TextView)findViewById(R.id.password3_modify);

        btn_pass = (Button)findViewById(R.id.btn_pass);
        btn_modify = (Button) findViewById(R.id.btn_modify);
    }
}