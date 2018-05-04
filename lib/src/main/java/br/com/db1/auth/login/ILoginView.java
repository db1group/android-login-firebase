package br.com.db1.auth.login;

import android.content.Intent;
import android.support.annotation.StringRes;

import com.facebook.login.LoginManager;

import br.com.db1.mvparchitecture.view.IView;

/**
 * Created by bruno.trovo on 03/07/2017.
 */
public interface ILoginView extends IView {

    void setUsernameFieldError(@StringRes int error);

    void setPasswordFieldError(@StringRes int error);

    void clearFieldErrors();

    void startFacebookLoginActivity(LoginManager facebookLoginManager);

    void startGoogleLoginActivity(Intent signInIntent);

}