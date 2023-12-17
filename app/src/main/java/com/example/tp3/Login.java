package com.example.tp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    TextView createCompt;
    TextInputEditText email, password;
    Button login;

    Button BGoogleSignIn  ;
    FirebaseAuth mAuth;
    ProgressBar pb;

    private GoogleSignInClient client ;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent in = new Intent(Login.this , MainActivity.class);
            startActivity(in);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createCompt = findViewById(R.id.createAcc);
        email = findViewById(R.id.email_Login);
        password = findViewById(R.id.Password_Login);
        login = findViewById(R.id.Blogin);
        pb = findViewById(R.id.PBLogin);

        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if (userEmail.equals("") || userPassword.equals("")) {
                    Toast.makeText(Login.this, "Write your email and password", Toast.LENGTH_SHORT).show();
                } else {
                    pb.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pb.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Login successful.", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(Login.this, MainActivity.class);
                                        startActivity(in);
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        createCompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SingUp.class));
            }
        });

        //------------------------------------------------------------------------------------------

        BGoogleSignIn = findViewById(R.id.BGoogleSignIn);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.token))

                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this,options);

        BGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // Sign out the Firebase user
                client.signOut();
                   Intent i = client.getSignInIntent();
                   startActivityForResult(i,1234);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    task.getException().printStackTrace();

                                    // Afficher le message d'erreur à l'utilisateur
                                    Toast.makeText(Login.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                // Afficher le code d'erreur
                int errorCode = e.getStatusCode();
                Log.e("GoogleSignIn", "Google sign-in failed with code: " + errorCode);
              System.out.println(errorCode);
                // Afficher un message d'erreur générique à l'utilisateur
                Toast.makeText(Login.this, "Google sign-in failed. Please try again.", Toast.LENGTH_SHORT).show();

                // Propager l'exception pour un examen plus approfondi si nécessaire
              //  throw new RuntimeException(e);
            }
        }
    }

}
