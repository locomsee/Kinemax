package com.example.collo.kinemax;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collo.kinemax.LoginActivity;
import com.example.collo.kinemax.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private Button forgot;
    private EditText textforgot;
    FirebaseAuth firebaseAuth;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot=(Button)findViewById(R.id.buttonforgot);
        textforgot=(EditText) findViewById(R.id.editTextforgotpassword);
        firebaseAuth=FirebaseAuth.getInstance();
        pb=(ProgressBar)findViewById(R.id.progressBar);

//action support
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Change Password");


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(v.VISIBLE);

                firebaseAuth.sendPasswordResetEmail(textforgot.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pb.setVisibility(View.GONE);

                        if(task.isSuccessful()) {

                            //toast message
                            Toast.makeText(ForgotPassword.this, "Reset link sent to email", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                            //go to login activity

                        }else {
                            Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            //GET exception meassage
                        }

                    }
                });

            }
        });
    }

}
