package com.example.monitoringsystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText username, password, emailAddr;
    Button registerBtn;
    FirebaseAuth firebaseAuth;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        emailAddr = findViewById(R.id.editText3);
        registerBtn = findViewById(R.id.buttonRegister);
        pBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String user = username.getText().toString();
               String pass = password.getText().toString();
               String mail = emailAddr.getText().toString();

               if(TextUtils.isEmpty(user))
               {
                   username.setError("A username is required");
                   return;
               }

               if(TextUtils.isEmpty(pass))
               {
                   password.setError("A password is required");
                   return;
               }

               if (password.length() < 6)
               {
                   password.setError("Password must contain at least 6 characters");
               }

               if (TextUtils.isEmpty(mail))
               {
                   emailAddr.setError("An E-mail address is required");
                   return;
               }

               pBar.setVisibility(View.VISIBLE);

               firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful())
                       {
                           Toast.makeText( Register.this, "New User Created", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       }
                       else
                       {
                           Toast.makeText(Register.this, "Error!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                           pBar.setVisibility(View.GONE);
                       }
                   }
               });
            }
        });

    }
}
