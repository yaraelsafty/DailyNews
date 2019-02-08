package com.example.yara.dailynews.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yara.dailynews.MainActivity;
import com.example.yara.dailynews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    private String TAG =LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private EditText tv_email;
    private EditText tv_password;
    private Button btn_logign;
    private TextView tv_create_new_account;
    private ProgressBar progressBar;

    private String email;
    private String password;
    private SharedPreferences prefs ;
    private   SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       tv_email=findViewById(R.id.tv_email);
       tv_password=findViewById(R.id.tv_password);
       btn_logign=findViewById(R.id.email_sign_in_button);
       tv_create_new_account=findViewById(R.id.tv_create_new_account);
       progressBar=findViewById(R.id.login_progress);

        //save user data
        prefs =this.getSharedPreferences(getString(R.string.ref_key),Context.MODE_PRIVATE);
        editor = prefs.edit();


       tv_create_new_account.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
               startActivity(intent);
           }
       });


       mAuth = FirebaseAuth.getInstance();

        btn_logign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                email=tv_email.getText().toString();
                password=tv_password.getText().toString();
                if (!email.isEmpty()&&!password.isEmpty())
                { login(email,password);
                }else
                    Toast.makeText(LoginActivity.this, getString(R.string.error_field_required), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            editor.putString(getString(R.string.id),user.getUid());
                            editor.putString(getString(R.string.email),user.getEmail());
                            editor.commit();
                            Log.d(TAG, user.getEmail()+"****"+user.getUid()+"****"+user.getProviderId());

                            Toast.makeText(LoginActivity.this, getString(R.string.Authentication_success),
                                    Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, getString(R.string.Authentication_failed),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

