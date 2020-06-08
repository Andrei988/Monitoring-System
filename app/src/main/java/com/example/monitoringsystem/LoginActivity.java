package com.example.monitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText emailLogin, passLogin;
    Button logBtn, signBtn;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
        emailLogin = findViewById(R.id.emailAddressBox);
        passLogin = findViewById(R.id.passwordBox);
        logBtn = findViewById(R.id.loginButton);
        signBtn = findViewById(R.id.signUpButton);

        firebaseAuth = FirebaseAuth.getInstance();

        logBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String pass = passLogin.getText().toString();
                String mail = emailLogin.getText().toString();

                if(TextUtils.isEmpty(pass))
                {
                    passLogin.setError("A password is required");
                    return;
                }

                if (passLogin.length() < 6)
                {
                    passLogin.setError("Password must contain at least 6 characters");
                }

                if (TextUtils.isEmpty(mail))
                {
                    emailLogin.setError("An E-mail address is required");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText( LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Error!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }



}