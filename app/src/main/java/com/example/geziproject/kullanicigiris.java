package com.example.geziproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class kullanicigiris extends AppCompatActivity {
    Button btngiris;
    Button btnkayit;
    FirebaseAuth mFirebaseAuth;
    private EditText txtad;
    private EditText txtemail;
    private EditText txtsifre;
     private  FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanicigiris);
         mFirebaseAuth=FirebaseAuth.getInstance();
        Button btngiris = findViewById(R.id.giris);
        Button btnkayit = findViewById(R.id.kayit);

        txtad = findViewById(R.id.txtad);
        txtemail = findViewById(R.id.txtemaili);
        txtsifre = findViewById(R.id.txtŞifre);


        mAuthListener= firebaseAuth -> {
         FirebaseUser mFirebaseUser =mFirebaseAuth.getCurrentUser();
        if(mFirebaseUser!=null){
        Toast.makeText(kullanicigiris.this,"giriş yaptın",Toast.LENGTH_LONG).show();
        Intent i=new Intent(kullanicigiris.this,bos.class);
        startActivity(i);
            }
            else{
                Toast.makeText(kullanicigiris.this,"lütfen giriş yapın",Toast.LENGTH_LONG).show();
            }
        };


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
             Toast.makeText(kullanicigiris.this,"Bu alanlar boş bırakılamaz",Toast.LENGTH_LONG).show();
         }
         else if(!(email.isEmpty() && pwd.isEmpty())){
             mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(kullanicigiris.this, task -> {
                if(!task.isSuccessful()){
                    Toast.makeText(kullanicigiris.this,"Giriş  başarısız ,tekrar deneyiniz",Toast.LENGTH_LONG).show();
                }
                else{
                    txtad.setText("");
                    txtemail.setText("");
                    txtsifre.setText("");
                       startActivity(new Intent(kullanicigiris.this,bos.class));
                }
             });
         }
         else{
             Toast.makeText(kullanicigiris.this,"Hata oluştu",Toast.LENGTH_LONG).show();
         }
        });

        btnkayit.setOnClickListener(v -> {
            Intent i= new Intent(kullanicigiris.this,kayitol.class);
            startActivity(i);
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);

    }

    }











