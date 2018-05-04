package br.com.db1.auth.forgotpassword;

import br.com.db1.mvparchitecture.presenter.BasePresenter;

/**
 * Created by vinicius.camargo on 24/04/2018.
 */
public abstract class ForgotPasswordPresenter extends BasePresenter {

   protected abstract void requestPassword(String username);

}
