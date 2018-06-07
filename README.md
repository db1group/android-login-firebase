
# Android Login with Firebase Integration

With this library you can improve your app using a login with firebase integration and a custom login focusing only in the responses of each flow.

[![](https://jitpack.io/v/db1global/android-login-firebase.svg)](https://jitpack.io/#db1global/android-login-firebase)

## Structure

* ForgotPassword: A pair Presenter-View that implements activity flow and you only need to set the message of wrong password and flow to recover password;
* Login: A pair Presenter-View that implements activity flow and you only need to set ForgotPasswordActivityImplementer and specify the behavior of each possible login result (success or failure);
* Resources: 
  * Two default layouts (portrait and landscape) for login and forgot password;
  * Colors for button background, text and ripple effect;
  * Two booleans to disable forgot password or login with firebase.

## Dependencies
This project depends on the following libraries
* Butterknife and its compiler
* Firebase auth and ui-auth
* Google play services
* Facebook login sdk

## Download and usage

Get the latest artifact via gradle
```groovy
implementation 'com.github.db1global:android-login-firebase:1.0.0'
```
To use in your project your must extends the classes below and implement their abstracts methods:
* LoginActivity
* LoginPresenter
* ForgotPasswordActivity
* ForgotPasswordPresenter

If you want to do provide firebase login:
* provide a properly configured *google-service.json* in app/
* put in AndroidManifest:
```groovy <meta-data  
  android:name="com.facebook.sdk.ApplicationId"  
  android:value="@string/facebook_app_id"  
  tools:replace="android:value" /> 
  ```
  * override the strings:
    * *google_server_client_id*  - more details: https://goo.gl/7EM1jd
    * *facebook_app_id* - more details: https://goo.gl/GC5Vog
     * *fb_login_protocol_scheme* - more details: https://goo.gl/GC5Vog

## License


