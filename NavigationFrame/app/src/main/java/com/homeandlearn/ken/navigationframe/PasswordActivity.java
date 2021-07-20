package com.homeandlearn.ken.navigationframe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText Email;
    private Button Reset;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Email = (EditText) findViewById(R.id.etEmail);
        Reset = (Button) findViewById(R.id.btnReset);

        firebaseAuth = FirebaseAuth.getInstance();

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email.getText().toString().trim();

                if (email.equals("")) {
                    Toast.makeText(PasswordActivity.this, "Please Enter Your Register Email ID", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(PasswordActivity.this, "Check Your E-mail", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PasswordActivity.this, "Enter Valid E-mail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}