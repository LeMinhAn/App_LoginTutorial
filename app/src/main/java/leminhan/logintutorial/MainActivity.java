package leminhan.logintutorial;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    private TextView txt_app, txt_slogan, txt_join, txt_private, txt_by, txt_signup;
    private Typeface tf_pro_black, tf_extralight, tf_light_italic;
    private EditText ed_email, ed_password;
    private AppCompatButton btn_login;
    private ImageView iv_facebook, iv_googleplus;
    CallbackManager callbackManager;
    LoginButton loginButton;

    private SignInButton btnSignIn;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private static final int RC_SIGN_IN = 9001;
    AlertDialog dialogDetails = null;
    View dialogview;
    Button btn_logout, btn_disconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());


        initValues();
        initViews();
        initAction();


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            Log.d("tag", "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
////                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
//    }

    private void initValues() {
        txt_app = (TextView) findViewById(R.id.tv_app);
        txt_slogan = (TextView) findViewById(R.id.tv_slogan);
        txt_join = (TextView) findViewById(R.id.tv_join);

        txt_private = (TextView) findViewById(R.id.tv_private);
        txt_by = (TextView) findViewById(R.id.tv_by);
        txt_signup = (TextView) findViewById(R.id.tv_signup);

        ed_email = (EditText) findViewById(R.id.input_email);
        ed_password = (EditText) findViewById(R.id.input_password);
        btn_login = (AppCompatButton) findViewById(R.id.btn_login);

        iv_facebook = (ImageView) findViewById(R.id.iv_facebook);
        iv_googleplus = (ImageView) findViewById(R.id.iv_gooogle_plus);
        loginButton = (LoginButton) findViewById(R.id.login_button);


        LayoutInflater inflater = LayoutInflater.from(this);
        dialogview = inflater.inflate(R.layout.status_login, null);

        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setTitle("Login");
        dialogbuilder.setView(dialogview);
        dialogDetails = dialogbuilder.create();

        btn_logout = (Button) dialogview.findViewById(R.id.sign_out_button);
        btn_disconnect = (Button) dialogview.findViewById(R.id.disconnect_button);

        tf_pro_black = Typeface.createFromAsset(getAssets(), "source-sans-pro.black.ttf");
        tf_extralight = Typeface.createFromAsset(getAssets(), "source-sans-pro.extralight.ttf");
        tf_light_italic = Typeface.createFromAsset(getAssets(), "source-sans-pro.light-italic.ttf");
    }


    private void initViews() {
        txt_app.setTypeface(tf_pro_black);
        txt_slogan.setTypeface(tf_extralight);
        txt_join.setTypeface(tf_light_italic);
        txt_signup.setTypeface(tf_extralight);
        txt_private.setTypeface(tf_extralight);
        txt_by.setTypeface(tf_extralight);


    }

    private void initAction() {
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == iv_facebook) {
                    loginButton.performClick();
                }
            }
        });
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);
                    }
                });

        iv_googleplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revokeAccess();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
//        else {
//            Dialog customIngsDlg = new Dialog(this);
//            customIngsDlg.setContentView(R.layout.status_login);
//            customIngsDlg.setTitle("Bạn có muốn đăng xuất");
//            Button btn_logout = (Button) customIngsDlg.findViewById(R.id.sign_out_button);
//            Button btn_disconect = (Button) customIngsDlg.findViewById(R.id.disconnect_button);
//            btn_logout.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    signOut();
//                }
//            });
//            btn_disconect.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    revokeAccess();
//                }
//            });
//            customIngsDlg.show();
//        }
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        btn_login.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public boolean validate() {
        boolean valid = true;

        String email = ed_email.getText().toString();
        String password = ed_password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ed_email.setError("enter a valid email address");
            valid = false;
        } else {
            ed_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            ed_password.setError("password biiger 4 character");
            valid = false;
        } else {
            ed_password.setError(null);
        }

        return valid;
    }

    public void onLoginSuccess() {
        btn_login.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btn_login.setEnabled(true);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("tag", "onConnectionFailed:" + connectionResult);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("tag", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            updateUI(true);

        } else {
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            dialogDetails.show();
        } else {
            dialogDetails.dismiss();
        }
    }
}
