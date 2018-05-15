package br.com.db1.auth.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;

import com.facebook.login.LoginManager;

import java.util.Collections;

import br.com.db1.auth.R;
import br.com.db1.auth.R2;
import br.com.db1.auth.forgotpassword.ForgotPasswordActivity;
import br.com.db1.mvp.util.StringUtils;
import br.com.db1.mvp.view.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bruno.trovo on 20/06/2017.
 */
public abstract class LoginActivity extends BaseActivity implements ILoginView {

    private static final String FACEBOOK_EMAIL_PERMISSION = "email";

    public static final String KEY_USERNAME = "KEY_USERNAME";

    @BindView(R2.id.act_login_til_username)
    TextInputLayout tilUsername;

    @BindView(R2.id.act_login_et_login)
    TextInputEditText etLogin;

    @BindView(R2.id.act_login_til_password)
    TextInputLayout tilPassword;

    @BindView(R2.id.act_login_et_password)
    TextInputEditText etPassword;

    @BindView(R2.id.btn_login_google)
    Button btnGoogle;

    @BindView(R2.id.btn_login_facebook)
    Button btnFacebook;

    LoginPresenter presenter;

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_login;
    }

    @Override
    protected Boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializeComponents() {
        ButterKnife.bind(this);
        clearFieldErrors();

        String username = getIntent().getStringExtra(KEY_USERNAME);
        if (StringUtils.isNotEmpty(username)) {
            etLogin.setText(username);
            tilPassword.requestFocus();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnGoogle.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_google),
                    null,
                    null, null);
            btnFacebook.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_facebook_box),
                    null,
                    null, null);
        }

        presenter = getPresenterImpl();

        presenter.attachView(this);
    }

    @Override
    protected void terminateComponents() {
        presenter.detachView();
    }

    @OnClick(R2.id.btn_login_facebook)
    public void onFacebookClicked() {
        presenter.loginWithFacebook();
    }

    @OnClick(R2.id.btn_login_google)
    public void onGoogleClicked() {
        presenter.loginWithGoogle();
    }

    @OnClick(R2.id.act_login_btn_login)
    public void onLoginClicked() {
        presenter.login(etLogin.getText().toString(), etPassword.getText().toString());
    }

    @OnClick(R2.id.act_login_tv_forgot_password)
    public void onForgotPasswordClicked() {
        startActivity(ForgotPasswordActivity.newIntent(this,
                getForgotPasswordActivityClass()));
    }

    @Override
    public void startFacebookLoginActivity(LoginManager facebookLoginManager) {
        facebookLoginManager.logInWithReadPermissions(this,
                Collections.singletonList(FACEBOOK_EMAIL_PERMISSION));
    }

    @Override
    public void startGoogleLoginActivity(Intent signInIntent) {
        startActivityForResult(signInIntent, LoginPresenter.RC_GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResultHandle(requestCode, resultCode, data);
    }

    public void setUsernameFieldError(@StringRes int error) {
        tilUsername.setError(getString(error));
    }

    public void setPasswordFieldError(@StringRes int error) {
        tilPassword.setError(getString(error));
    }

    public void clearFieldErrors() {
        tilUsername.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
    }

    public abstract LoginPresenter getPresenterImpl();

    public abstract Class<? extends ForgotPasswordActivity> getForgotPasswordActivityClass();
}