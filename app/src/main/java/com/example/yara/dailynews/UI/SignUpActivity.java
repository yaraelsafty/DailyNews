package com.example.yara.dailynews.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yara.dailynews.MainActivity;
import com.example.yara.dailynews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private String TAG=SignUpActivity.class.getSimpleName();

    private EditText tv_email;
    private EditText tv_password;
    private FirebaseAuth mAuth;
    private Button btn_sing_up;
    private ProgressBar progressBar;

    String email=null;
    String password=null;
    private SharedPreferences prefs ;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tv_email=findViewById(R.id.tv_SignUp_email);
        tv_password=findViewById(R.id.tv_SignUp_password);
        btn_sing_up=findViewById(R.id.btn_SignUp);
        progressBar=findViewById(R.id.SignUp_progress);


        mAuth = FirebaseAuth.getInstance();

        //save user data
        prefs =this.getSharedPreferences(getString(R.string.ref_key), Context.MODE_PRIVATE);
        editor = prefs.edit();


        btn_sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                email=tv_email.getText().toString().trim();
                password=String.valueOf(tv_password.getText());
                if (Validation()){
                createUser(email,password);}
            }
        });



    }

    private void createUser(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d(TAG, getResources().getString(R.string.Authentication_success)+user.getDisplayName());

                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.Authentication_success),
                            Toast.LENGTH_SHORT).show();
                    editor.putString(getString(R.string.id),user.getUid());
                    editor.putString(getString(R.string.email),user.getEmail());
                    editor.commit();
                    Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, getResources().getString(R.string.Authentication_failed), task.getException());
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.Authentication_failed),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private Boolean Validation() {
        if (email.isEmpty()){
            tv_email.setError(getResources().getString(R.string.error_field_required));
            return false;
        }
        if (password.isEmpty()){
            tv_password.setError(getResources().getString(R.string.error_field_required));
            return false;
        }else {
            return true;

        }
    }
}
