package com.example.geziproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private Button signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Button signout;
    Button btngiris;
    Button btnkayit;
    private EditText txtad;
    private EditText txtemail;
    private EditText txtsifre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btngiris = findViewById(R.id.btngiris);
        Button btnkayit = findViewById(R.id.btnkayit);


        txtemail = findViewById(R.id.txtemail);
        txtsifre = findViewById(R.id.txtsifre);
        signInButton = findViewById(R.id.signin);
        mAuth = FirebaseAuth.getInstance();
       // signout = findViewById(R.id.sign_out);

        btngiris.setOnClickListener(v -> {
            String email=txtemail.getText().toString();
            String pwd= txtsifre.getText().toString();
            if(email.isEmpty()){
                txtemail.setError("Lütfen email giriniz");
                txtemail.requestFocus();
            }
            else if(pwd.isEmpty()){
                txtsifre.setError("Lütfen şifre giriniz");
                txtsifre.requestFocus();
            }
            else if(email.isEmpty() && pwd.isEmpty())
            {
                Toast.makeText(LoginActivity.this,"Bu alanlar boş bırakılamaz",Toast.LENGTH_LONG).show();
            }
            else if(!(email.isEmpty() && pwd.isEmpty())){
                mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity.this, task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Giriş  başarısız ,tekrar deneyiniz",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Giriş  başarılı",Toast.LENGTH_LONG).show();
                        txtemail.setText("");
                        txtsifre.setText("");

                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                });
            }
            else{
                Toast.makeText(LoginActivity.this,"Hata oluştu",Toast.LENGTH_LONG).show();
            }
        });
        btnkayit.setOnClickListener(v -> {
            Intent i= new Intent(LoginActivity.this,kayitol.class);
            startActivity(i);
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1088981466528-8pkjha8350r2uniqg2425nv5itvgpvr7.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(v -> signIn());


        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //handleSignInResult(task);

            if (task.isSuccessful()) {
                String s = "Google sign in Successful";
                displayToast(s);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Toast.makeText(getApplicationContext(), "giriş başarılı", Toast.LENGTH_LONG).show();

                    if (account != null) {
                        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });

                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }


            }

        }

    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

    }

}














