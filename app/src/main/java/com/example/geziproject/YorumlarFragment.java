package com.example.geziproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class YorumlarFragment extends Fragment {
    EditText edit_yorumlar;
    ImageView profilresmi_yorumlar;
    TextView gonder;
    String gonderi_id;
    String gonderen_id;
    FirebaseUser mevcutkullanici;



    public YorumlarFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_yorumlar, container, false);
        Toolbar toolbar=(Toolbar) rootview.findViewById(R.id.toolbar_yorumlar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Yorumlar");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().finish());
        edit_yorumlar=(EditText) rootview.findViewById(R.id.edit_yorumlar);
        profilresmi_yorumlar=(ImageView) rootview.findViewById(R.id.profilresmi_yorumlar);
        gonder=(TextView) rootview.findViewById(R.id.gonder);

      //  Intent intent=Intent.getIntentOld();
        Bundle args = getArguments();
        gonderi_id = args.getString("gonderiId");
        gonderen_id=args.getString("gonderenId");

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_yorumlar.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"Boş yorum gönderemezsiniz",Toast.LENGTH_LONG).show();

                }
                else{
                    yorumEkle();
                }

            }


        });


        return rootview;
    }

    private void yorumEkle() {
        DatabaseReference yorumlarYolu= FirebaseDatabase.getInstance().getReference("yorumlar").child(gonderi_id);
        HashMap <String,Object> hashMap=new HashMap<>();
        hashMap.put("yorum",edit_yorumlar.getText().toString());
        hashMap.put("gonderen",mevcutkullanici.getUid());
        yorumlarYolu.push().setValue(hashMap);
        edit_yorumlar.setText("");

    }
    private void resimAl(){
        DatabaseReference resimYolu=FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mevcutkullanici.getUid()) ;
        resimYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // Kullanici kullanici=snapshot.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}