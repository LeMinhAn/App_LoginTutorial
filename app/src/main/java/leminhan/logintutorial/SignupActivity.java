package leminhan.logintutorial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private EditText ed_email, ed_password, ed_name;
    private TextView txt_back_login;
    private Typeface tf_extralight;
    private AppCompatButton btn_creat_acount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        initValues();
        initViews();
        initAction();

    }

    private void initValues() {
        txt_back_login = (TextView) findViewById(R.id.link_login);
        ed_email = (EditText) findViewById(R.id.input_email);
        ed_password = (EditText) findViewById(R.id.input_password);
        ed_name = (EditText) findViewById(R.id.input_name);
        btn_creat_acount = (AppCompatButton) findViewById(R.id.btn_signup);
        tf_extralight = Typeface.createFromAsset(getAssets(), "source-sans-pro.extralight.ttf");
    }

    private void initViews() {

        txt_back_login.setTypeface(tf_extralight);
    }

    private void initAction() {
        txt_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_creat_acount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }
        btn_creat_acount.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
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
        String name = ed_name.getText().toString();
        String email = ed_email.getText().toString();
        String password = ed_password.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            ed_name.setError("at least 3 characters");
            valid = false;
        } else {
            ed_name.setError(null);
        }
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
        btn_creat_acount.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btn_creat_acount.setEnabled(true);
    }
}
