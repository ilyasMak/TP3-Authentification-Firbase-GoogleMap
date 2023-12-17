package com.example.tp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUp extends AppCompatActivity {
    TextInputEditText email , password ;
    Button SignUp ;
    FirebaseAuth  mAuth ;
    ProgressBar pb ;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent in = new Intent(SingUp.this , MainActivity.class);
            startActivity(in);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        email = findViewById(R.id.Email_Sign_Up);
        password = findViewById(R.id.Password_SignUp);
        SignUp = findViewById(R.id.SignUp);
        pb = findViewById(R.id.PBsignUp);
   //-----------------------------------------------------
        mAuth = FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 pb.setVisibility(View.VISIBLE);
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if(userEmail.equals("")|| userPassword.equals(""))
                    Toast.makeText(SingUp.this, "Write your email and password ", Toast.LENGTH_SHORT).show();

                //------------------------
                mAuth.createUserWithEmailAndPassword(userEmail , userPassword)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pb.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(SingUp.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(SingUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }


}