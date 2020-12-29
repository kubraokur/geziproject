package com.example.geziproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;

public class kayitol extends AppCompatActivity {
  private EditText txtAd,txtEmail,txtŞifre;
  private Button btnkayit;
   FirebaseAuth mFirebaseAuth;
   //DatabaseReference yol;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitol);
        mFirebaseAuth=FirebaseAuth.getInstance();
        txtAd= findViewById(R.id.txtad);
        txtEmail= findViewById(R.id.txtEmail);
        txtŞifre= findViewById(R.id.txtŞifre);
        btnkayit= findViewById(R.id.kayit);



      btnkayit.setOnClickListener(v -> {
          String email=txtEmail.getText().toString();
          String pwd= txtŞifre.getText().toString();
          if(email.isEmpty()){
              txtEmail.setError("Lütfen email giriniz");
              txtEmail.requestFocus();
          }
          else if(pwd.isEmpty()){
              txtŞifre.setError("Lütfen şifre giriniz");
              txtŞifre.requestFocus();
          }
          else if(email.isEmpty() && pwd.isEmpty())
          {
              Toast.makeText(kayitol.this,"Bu alanlar boş bırakılamaz",Toast.LENGTH_LONG).show();
          }
          else if(!(email.isEmpty() && pwd.isEmpty())){
              mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(kayitol.this, task -> {
               if(!task.isSuccessful()){
                   Toast.makeText(kayitol.this,"Kayıt başarısız,tekrar deneyiniz",Toast.LENGTH_LONG).show();
               }
               else{
                   Intent intent=new Intent(kayitol.this,MainActivity.class);
                  startActivity(intent);
               }
              });
          }
          else{
              Toast.makeText(kayitol.this,"Hata oluştu",Toast.LENGTH_LONG).show();
          }
      });


    }


}