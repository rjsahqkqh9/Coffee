/*
 *  Create by platform eight on 2020. 6. 10.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the register form.
 */
class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer password2Error;
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer phoneError;

    private boolean isDataValid;
    /*
    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }
    */
    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer password2Error, @Nullable Integer nameError, @Nullable Integer phoneError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.password2Error = password2Error;
        this.nameError = nameError;
        this.phoneError = phoneError;
        this.isDataValid = false;
    }
    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.password2Error = null;
        this.nameError = null;
        this.phoneError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
    @Nullable
    Integer getPassword2Error() { return password2Error; }
    @Nullable
    Integer getNameError() {
        return nameError;
    }

    @Nullable
    Integer getPhoneError() {
        return phoneError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}