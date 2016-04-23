package com.maidangtung.soccernetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maidangtung.soccernetwork.R;

public class SignUpActivity extends AppCompatActivity {


    EditText _emailText;
    EditText _passwordText;
    EditText _passwordText_confirm;
    Button _signUpButton;
    TextView _link_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponent();
    }
    private void initComponent(){
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _passwordText_confirm = (EditText) findViewById(R.id.input_password_confirm);
        _signUpButton = (Button) findViewById(R.id.btn_signup);
        _link_login = (TextView) findViewById(R.id.link_login);

        _link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
        _signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onSignUpSuccess(){
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
        finish();
    }
    private void signup(){
        if (!validate()){
            onSignUpFailed();
            return;
        }
        final ProgressDialog waiting = new ProgressDialog(SignUpActivity.this,R.style.MyMaterialTheme);
        waiting.setIndeterminate(true);
        waiting.setMessage("Signup ...");
        waiting.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onSignUpSuccess();
                        waiting.dismiss();
                    }
                },
                3000);
    }

    public void onSignUpFailed(){
        Toast.makeText(getBaseContext(), "Sign Failed", Toast.LENGTH_SHORT).show();
        _signUpButton.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String password_confirm = _passwordText_confirm.getText().toString();

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

        if (!password.equals(password_confirm)){
            _passwordText_confirm.setError("Comfirm password did not match");
            Toast.makeText(getBaseContext(), (password+password_confirm), Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            _passwordText_confirm.setError(null);
        }
        return valid;
    }
}