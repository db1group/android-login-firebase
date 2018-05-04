package br.com.db1.auth.forgotpassword;

import android.support.annotation.StringRes;

import br.com.db1.mvp.view.IView;

/**
 * Created by bruno.trovo on 03/07/2017.
 */
public interface IForgotPasswordView extends IView {

    void clearFieldErrors();

    void setUsernameFieldError(@StringRes int error);

    void showForgotPasswordError(Throwable throwable);

}
