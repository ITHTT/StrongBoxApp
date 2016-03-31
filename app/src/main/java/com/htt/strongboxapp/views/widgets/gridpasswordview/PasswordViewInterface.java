package com.htt.strongboxapp.views.widgets.gridpasswordview;


/**
 * Created by HTT on 2015/5/26.
 */
public interface PasswordViewInterface {
    String getPassWord();

    void clearPassword();

    void setPassword(String password);

    void setPasswordVisibility(boolean visible);

    void togglePasswordVisibility();

    void setOnPasswordChangedListener(GridPasswordView.OnPasswordChangedListener listener);

    void setPasswordType(PasswordType passwordType);
}
