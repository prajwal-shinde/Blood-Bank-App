package com.homeandlearn.ken.navigationframe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.homeandlearn.ken.navigationframe.LoginActivity;
import com.homeandlearn.ken.navigationframe.R;
import com.homeandlearn.ken.navigationframe.UserProfile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText UserName,Address;
    private EditText Password,Cpassword;
    private EditText Email,Phone;
    private TextView Login;
    private Button Register;
    private FirebaseAuth firebaseAuth;
    private ImageView Logo;
    String name,password,address,email,phone,cpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupUIViews();

        firebaseAuth= FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    String email=Email.getText().toString().trim();
                    String password=Password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                sendEmailVerification();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupUIViews() {

        UserName= (EditText) findViewById(R.id.etUserName);
        Password = (EditText) findViewById(R.id.etPassword);
        Register = (Button) findViewById(R.id.btnRegister);
        Login = (TextView) findViewById(R.id.tvLogin);
        Email = (EditText) findViewById(R.id.etEmail);
        Cpassword= (EditText) findViewById(R.id.etCpassword);
        Address= (EditText) findViewById(R.id.etAddress);
        Phone= (EditText) findViewById(R.id.etPhone);
        Logo=(ImageView) findViewById(R.id.imageView);
    }

    private Boolean validate() {
        Boolean result = false;

        name = UserName.getText().toString();
        password = Password.getText().toString();
        email = Email.getText().toString();
        address = Address.getText().toString();
        phone = Phone.getText().toString();
        cpassword = Cpassword.getText().toString();
        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || address.isEmpty() || cpassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter the detail", Toast.LENGTH_SHORT).show();
        }else if(phone.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Enter Mobile Number",Toast.LENGTH_SHORT).show();
        }else if (phone.length() < 10 || phone.length() > 10) {
            Toast.makeText(RegisterActivity.this,"Enter 10 Digit Only",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendEmailVerification() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {


                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(RegisterActivity.this, "Successfully Registered, Verification E-mail", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Register Failed, Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }
    private void sendUserData()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile=new UserProfile(name,email,phone,address,password);
        myRef.setValue(userProfile);
    }
}
