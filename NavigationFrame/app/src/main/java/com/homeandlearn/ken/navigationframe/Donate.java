package com.homeandlearn.ken.navigationframe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Donate extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button b1;
    private EditText e1,e2,e3,e4;
    private Spinner spinner;
    private Firebase mRef;
    private RadioButton r1,r2;
    private ProgressDialog progressDialog;
    private RadioGroup rg1;
    private DrawerLayout drawerLayout;
    private RadioButton radioButton;
    String s,s1,s3,s4,item,s5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        FirebaseAuth firebaseauth = FirebaseAuth.getInstance();
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView tt1 = (TextView) headerview.findViewById(R.id.user_1);
        tt1.setText(firebaseauth.getCurrentUser().getEmail());
        navigationView.setNavigationItemSelectedListener(this);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://databse2.firebaseio.com/");
        b1 = (Button) findViewById(R.id.btn_submit);
        e1 = (EditText) findViewById(R.id.e_fn);
        e2 = (EditText) findViewById(R.id.e_ln);
        e3 = (EditText) findViewById(R.id.e_email);
        e4 = (EditText) findViewById(R.id.e_mbno);
        spinner = (Spinner) findViewById(R.id.sp_bldgrp);
        r1 = (RadioButton) findViewById(R.id.rg_platelate);
        r2 = (RadioButton) findViewById(R.id.rg_blood);
        rg1=(RadioGroup)findViewById(R.id.rg);
        List<String>BloodGroup= new ArrayList<>();
        BloodGroup.add(0,"Select your Blood Group");
        BloodGroup.add("A+");
        BloodGroup.add("B+");
        BloodGroup.add("AB+");
        BloodGroup.add("O+");
        BloodGroup.add("AB-");
        BloodGroup.add("O-");
        BloodGroup.add("B-");
        BloodGroup.add("A-");
        ArrayAdapter<String> dataAdapter;
        dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,BloodGroup);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        progressDialog = new ProgressDialog(this);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(adapterView.getItemAtPosition(i).equals("Select your Blood Group") )
                {

                }
                else
                {
                    item=adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(Donate.this,"Selected Blood Group:"+item,Toast.LENGTH_SHORT ).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Firebase mRefChild1 = mRef.child("User");
                 s=e1.getText().toString();
                 s1=e2.getText().toString();
                 s3=e3.getText().toString();
                 s4=e4.getText().toString();
                int selectedId = rg1.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if(radioButton!=null ){
                 s5=radioButton.getText().toString();}
                UserData userData =new UserData(s,s1,s3,s4,item,s5);
                progressDialog.setMessage("Registering Details");
                progressDialog.show();
                Intent i=new Intent(Donate.this,MainActivity.class );
                startActivity(i);
                mRefChild1.push().setValue(userData);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i=new Intent(Donate.this,MainActivity.class );
            startActivity(i);
        } else if(id==R.id.nav_search)
        {
            Intent i=new Intent(Donate.this, MapsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_help) {
            Intent i=new Intent(Donate.this,Help.class );
            startActivity(i);
        } else if (id == R.id.nav_db) {
            Intent i=new Intent(Donate.this,Donate.class );
            startActivity(i);
        } else if (id == R.id.nav_Rb) {
            Intent i=new Intent(Donate.this,RequestActivity.class );
            startActivity(i);
        }else if(id==R.id.nav_about){
            Intent i=new Intent(Donate.this,About.class);
            startActivity(i);
        }else if(id==R.id.nav_rb){
            Intent i=new Intent(Donate.this,Request2.class);
            startActivity(i);
        }else if(id==R.id.nav_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String ShareBody="Your Body Here";
            String Sharesub="Your Subject Here";
            intent.putExtra(Intent.EXTRA_SUBJECT,Sharesub);
            intent.putExtra(Intent.EXTRA_TEXT,ShareBody);
            startActivity(Intent.createChooser(intent,"Share Using"));
            Toast.makeText(this, "Share Application", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.nav_feed){
            Intent intent=new Intent(Intent.ACTION_SEND);
            String[] to={"Enter G-mail Here"};
            intent.putExtra(Intent.EXTRA_EMAIL,to);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback Of Application");
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.mail));
            intent.setType("nessage/rfc822");
            startActivity(Intent.createChooser(intent,"Send Email"));
        }
        DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
