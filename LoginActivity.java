package com.maidangtung.soccernetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.maidangtung.soccernetwork.R;
//import com.maidangtung.soccernetwork.RestFul;
//import com.example.marsch.soccersocial.models.User;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _linkSignup;
    CheckBox _rememberMe;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
    }
    private void initComponent(){
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _linkSignup = (TextView) findViewById(R.id.link_signup);
        _rememberMe = (CheckBox) findViewById(R.id.cb_remember);
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login();
            }
        });

        _linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(signup, REQUEST_SIGNUP);
            }
        });
    }

    /*private void login(){
        if (!validate()){
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);

        final ProgressDialog waiting = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        waiting.setIndeterminate(true);
        waiting.setMessage("Authenticating...");
        waiting.show();
        RestFul api = new RestFul();
        User user = new User();

        user.setEmail(_emailText.getText().toString());
        user.setPassword(_passwordText.getText().toString());
        api.setUser(user);

        RestFul.HttpAsyncTask authentication = api.new HttpAsyncTask();
        authentication.execute("http://skymarsch.xyz/login/");

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onLoginSuccess();
                        waiting.dismiss();
                    }
                },
                3000);
    }*/

    public void savingPreferences()
    {
        SharedPreferences pre=getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        String email=_emailText.getText().toString();
        String pwd=_passwordText.getText().toString();

        boolean bchk=_rememberMe.isChecked();
        if(!bchk)
        {
            editor.clear();
        }
        else
        {
            editor.putString("email", email);
            editor.putString("pwd", pwd);
            editor.putBoolean("rememberMe", bchk);
        }
        editor.commit();
    }



    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess(){
        savingPreferences();
        finish();
    }

    public void onLoginFailed(){
        Toast.makeText(getBaseContext(),"Login Failed",Toast.LENGTH_SHORT).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()|| password.length()<4){
            _passwordText.setError("Password is too short");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }
}
