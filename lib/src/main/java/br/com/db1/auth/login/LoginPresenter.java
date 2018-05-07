package br.com.db1.auth.login;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import br.com.db1.auth.R;
import br.com.db1.auth.login.result.FacebookResult;
import br.com.db1.mvp.presenter.BasePresenter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by bruno.trovo on 20/06/2017.
 */
public abstract class LoginPresenter extends BasePresenter<ILoginView> {

    static final int RC_GOOGLE_SIGN_IN = 11;
    private static final int RC_FACEBOOK_SIGN_IN = 64206;

    private LoginManager facebookLoginManager;
    private CallbackManager facebookCallbackManager;
    private GoogleApiClient googleApiClient;
    private FragmentActivity activity;

    public LoginPresenter(FragmentActivity activity) {
        int isGooglePlayServicesAvailable =
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
        this.activity = activity;
        if (isGooglePlayServicesAvailable == ConnectionResult.SUCCESS) {
            facebookCallbackManager = CallbackManager.Factory.create();
            facebookLoginManager = LoginManager.getInstance();
            googleApiClient = buildGoogleApiClient();

            LinearLayout layout = activity.findViewById(R.id.layout_botoes_login);
            layout.setVisibility(View.VISIBLE);
            registerFacebookLoginCallback();
        }

    }

    private GoogleApiClient buildGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(
                        activity.getString(R.string.google_server_client_id))
                .requestEmail()
                .build();

        return new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, connectionResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    void onActivityResultHandle(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_GOOGLE_SIGN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleGoogleSignInResult(task);
                break;
            case RC_FACEBOOK_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    executeFacebookCallback(requestCode, resultCode, data);
                }
                break;
            default:
                break;
        }
    }


    private void registerFacebookLoginCallback() {
        facebookLoginManager.registerCallback(facebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getView().showProgress(R.string.act_login_login_progress_message);
                        loginWithFacebookSuccess(new FacebookResult(
                                loginResult.getAccessToken().getToken(),
                                loginResult.getAccessToken().getUserId(),
                                loginResult.getAccessToken().getApplicationId()));
                        getView().hideProgress();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                        loginWithFacebookFailed();
                    }
                });
    }

    private void executeFacebookCallback(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Boolean isLoggedInWithFacebook() {
        return AccessToken.getCurrentAccessToken() != null
                && Profile.getCurrentProfile() != null;
    }

    private void logoutFacebook() {
        facebookLoginManager.logOut();
    }

    void loginWithFacebook() {
        if (isLoggedInWithFacebook()) {
            logoutFacebook();
        }
        getView().startFacebookLoginActivity(facebookLoginManager);
    }


    void loginWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        getView().startGoogleLoginActivity(signInIntent);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            getView().showProgress(R.string.act_login_login_progress_message);
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            loginWithGoogleSuccess(account.getIdToken());
        } catch (ApiException e) {
            Log.e("API Error", "Sign in failed", e);
            loginWithGoogleFailed();
        } finally {
            getView().hideProgress();
        }
    }

    protected abstract void login(String username, String password);

    protected abstract void loginWithFacebookSuccess(FacebookResult loginResult);

    protected abstract void loginWithFacebookFailed();

    protected abstract void loginWithGoogleSuccess(String idToken);

    protected abstract void loginWithGoogleFailed();

}
