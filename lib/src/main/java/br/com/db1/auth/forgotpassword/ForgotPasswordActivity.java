package br.com.db1.auth.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import br.com.db1.auth.R;
import br.com.db1.auth.R2;
import br.com.db1.mvparchitecture.view.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bruno.trovo on 20/06/2017.
 */
public abstract class ForgotPasswordActivity extends BaseActivity implements IForgotPasswordView {

    @BindView(R2.id.act_forgot_password_til_username)
    TextInputLayout tilUsername;

    @BindView(R2.id.act_forgot_password_et_username)
    TextInputEditText etUsername;

    ForgotPasswordPresenter presenter;

    public static <T extends ForgotPasswordActivity> Intent newIntent(Context context, Class<T> forgotPasswordClass) {
        return new Intent(context, forgotPasswordClass);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected Boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected void initializeComponents() {
        initializeToolbar();
        clearFieldErrors();
        presenter = getPresenterImpl();
        presenter.attachView(this);
    }


    private void initializeToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void terminateComponents() {
        presenter.detachView();
    }

    @OnClick(R2.id.act_forgot_password_btn_forgot_password)
    public void onForgotPasswordButtonClicked() {
        presenter.requestPassword(etUsername.getText().toString());
    }

    public void clearFieldErrors() {
        tilUsername.setErrorEnabled(false);
    }

    @Override
    public void setUsernameFieldError(@StringRes int error) {
        tilUsername.setError(getString(error));
    }

    public abstract void showForgotPasswordError(Throwable throwable);

    public abstract ForgotPasswordPresenter getPresenterImpl();
}
