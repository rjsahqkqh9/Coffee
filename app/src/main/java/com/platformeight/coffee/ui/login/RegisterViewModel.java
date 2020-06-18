/*
 *  Create by platform eight on 2020. 6. 10.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee.ui.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platformeight.coffee.R;
import com.platformeight.coffee.data.LoginRepository;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private LoginRepository loginRepository;

    RegisterViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public void RegisterDataChanged(String username, String password, String password2, String name, String phone) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null, null, null, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password, null, null, null));
        } else if (!isPasswordValid2(password, password2)) {
            //Log.d("", "RegisterDataChanged: "+password+" "+password2+"  "+password.equals(password2));
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_password2, null, null));
        } else if (!isNameValid(name)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.invalid_input, null));
        } else if (!isPhoneValid(phone)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, R.string.invalid_input));
        } else {
            //Log.d("", "RegisterDataChanged: "+password+" "+password2+"  "+password.equals(password2));
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        /*
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
         */
        //return !username.trim().isEmpty();
        return username.trim().length() > 6;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }
    // A placeholder password validation check
    private boolean isPasswordValid2(String password, String password2) {
        return password2.equals(password);
    }
    // A placeholder password validation check
    private boolean isNameValid(String name) {
        if (name == null) {
            return false;
        }
        return !name.trim().isEmpty();
    }
    // A placeholder password validation check
    private boolean isPhoneValid(String phone) {
        return phone != null && phone.trim().length() == 13;
    }
}