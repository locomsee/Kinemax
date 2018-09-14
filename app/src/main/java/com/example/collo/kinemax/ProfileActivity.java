package com.example.collo.kinemax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }
        FirebaseUser user=firebaseAuth.getCurrentUser();

        textViewUserEmail=(TextView)findViewById(R.id.textViewuserEmail);

        textViewUserEmail.setText("Welcome "+user.getEmail());
        buttonLogOut=(Button)findViewById(R.id.buttonLogOut);

        buttonLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == buttonLogOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
