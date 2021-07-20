package com.homeandlearn.ken.navigationframe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private EditText e1;
    private EditText e2;
    private Button b1;
    private Firebase mRef;
    private RadioButton r1, r2, r3, r4;
    private Spinner s1, s2;
    private RadioGroup rg2,rg3;
    private ProgressDialog progressDialog;
    private RadioButton radioButton,radiobutton2;
    String a,b,c,d,e,f,item,item2;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
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
        mRef = new Firebase("https://navigationframe.firebaseio.com/");
        r1 = (RadioButton) findViewById(R.id.rd_bl);
        r2 = (RadioButton) findViewById(R.id.rd_pl);
        r3 = (RadioButton) findViewById(R.id.rd_stb);
        r4 = (RadioButton) findViewById(R.id.rd_crt);
        s1 = (Spinner) findViewById(R.id.sp_bl);
        s2 = (Spinner) findViewById(R.id.sp_no);
        rg2=(RadioGroup)findViewById(R.id.rg_1);
        rg3=(RadioGroup)findViewById(R.id.rg_3);
        e1=(EditText)findViewById(R.id.ed_fn);
        e2=(EditText)findViewById(R.id.ed_ln);
        progressDialog=new ProgressDialog(this);
        List<String> BloodGroup=new ArrayList<>();
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
        s1.setAdapter(dataAdapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(adapterView.getItemAtPosition(i).equals("Select your Blood Group") )
                {

                }
                else
                {
                    item=adapterView.getItemAtPosition(i).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        List<String> no_units=new ArrayList<>();
        no_units.add(0,"Select Number Of Units");
        no_units.add("1");
        no_units.add("2");
        no_units.add("3");
        no_units.add("4");
        no_units.add("5");
        no_units.add("6");
        no_units.add("7");
        no_units.add("8");
        no_units.add("9");
        no_units.add("10");
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2=new ArrayAdapter(this,android.R.layout.simple_spinner_item,no_units);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(dataAdapter2);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(adapterView.getItemAtPosition(i).equals("Select Number Of Units"))
                {

                }
                else
                {
                     item2=adapterView.getItemAtPosition(i).toString();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        e1 = (EditText) findViewById(R.id.ed_fn);
        e2 = (EditText) findViewById(R.id.ed_ln);
        b1 = (Button) findViewById(R.id.but_reg);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mRefChild1 = mRef.child("User");
                c=e1.getText().toString();
                d=e2.getText().toString();
                int selected=rg2.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selected);
                if(radioButton!=null ){
                    a=radioButton.getText().toString();}
                int selectedID=rg3.getCheckedRadioButtonId();
                radiobutton2 = (RadioButton) findViewById(selectedID);
                if(radiobutton2!=null ){
                    b=radiobutton2.getText().toString();}
                    UserData2 userData2=new UserData2(a,item,item2,b,c,d);
                    progressDialog.setMessage("Registering Details");
                    progressDialog.show();
                    Intent i=new Intent(RequestActivity.this,MainActivity.class );
                    startActivity(i);
                mRefChild1.push().setValue(userData2);
                }
            });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i=new Intent(RequestActivity.this,MainActivity.class );
            startActivity(i);
        } else if(id==R.id.nav_search)
        {
            Intent i=new Intent(RequestActivity.this, MapsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_help) {
            Intent i=new Intent(RequestActivity.this,Help.class );
            startActivity(i);
        } else if (id == R.id.nav_db) {
            Intent i=new Intent(RequestActivity.this,Donate.class );
            startActivity(i);
        } else if (id == R.id.nav_Rb) {
            Intent i=new Intent(RequestActivity.this,RequestActivity.class );
            startActivity(i);
        }else if(id==R.id.nav_about){
            Intent i=new Intent(RequestActivity.this,About.class);
            startActivity(i);
        }else if(id==R.id.nav_rb){
            Intent i=new Intent(RequestActivity.this,Request2.class);
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