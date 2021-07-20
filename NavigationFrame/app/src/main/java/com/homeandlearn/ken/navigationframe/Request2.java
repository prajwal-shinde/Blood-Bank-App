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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Request2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private Spinner s1,s2;
    private EditText e1,e2;
    private Button b1;
    private Firebase mRef;
    private ProgressDialog progressDialog;
    private String p,q,item1,item2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request2);
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
        mRef = new Firebase("https://digitalbloodbank-37be4.firebaseio.com/");
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e3);
        b1 = (Button) findViewById(R.id.b1);
        s1 = (Spinner) findViewById(R.id.s1);
        s2=(Spinner)findViewById(R.id.s2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                Firebase mRefChild1 = mRef.child("Users");
                p=e1.getText().toString();
                q=e2.getText().toString();
                UserData3 u1=new UserData3(item1,item2,p,q);
                mRefChild1.push().setValue(u1);
                 progressDialog=new ProgressDialog(Request2.this);
                progressDialog.setMessage("Please Wait..");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent i=new Intent(Request2.this,MainActivity.class);
                startActivity(i);
            }
            }
        });
        List<String> BloodGroup=new ArrayList<>();
        BloodGroup.add(0,"Select Blood Group");
        BloodGroup.add("A+");
        BloodGroup.add("B+");
        BloodGroup.add("AB+");
        BloodGroup.add("AB-");
        BloodGroup.add("O+");
        BloodGroup.add("O-");
        BloodGroup.add("A-");
        BloodGroup.add("B-");
        ArrayAdapter<String> dataAdapter ;
        dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,BloodGroup);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(dataAdapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Blood Group") )
                {

                }
                else
                {
                     item1 = parent.getItemAtPosition(position).toString();
                    Toast.makeText(Request2.this, "Selected Blood Group" + item1, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> Blood=new ArrayList<>();
        Blood.add(0,"Select District");
        Blood.add("Nashik");
        Blood.add("Mumbai");
        Blood.add("Nagpur");
        Blood.add("Pune");
        Blood.add("Satara");
        Blood.add("Thane");
        Blood.add("Nanded");
        Blood.add("Palghar");
        ArrayAdapter<String> data;
        data=new ArrayAdapter(this,android.R.layout.simple_spinner_item,Blood);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(data);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select District") )
                {

                }
                else
                {
                     item2 = parent.getItemAtPosition(position).toString();
                    Toast.makeText(Request2.this, "Selected District" + item2, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

}
private boolean validate(){
        if(e1.getText().toString().equals("")||e2.getText().toString().equals("")||item1.equals("")||item2.equals(""))
        {
            Toast.makeText(Request2.this,"Please Enter Details",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(e2.getText().length()>10){
            Toast.makeText(Request2.this,"Please Enter Correct Mobile Number",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i=new Intent(Request2.this,MainActivity.class );
            startActivity(i);
        } else if(id==R.id.nav_search)
        {
            Intent i=new Intent(Request2.this, MapsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_help) {
            Intent i=new Intent(Request2.this,Help.class );
            startActivity(i);
        } else if (id == R.id.nav_db) {
            Intent i=new Intent(Request2.this,Donate.class );
            startActivity(i);
        } else if (id == R.id.nav_Rb) {
            Intent i=new Intent(Request2.this,RequestActivity.class );
            startActivity(i);
        }else if(id==R.id.nav_about){
            Intent i=new Intent(Request2.this,About.class);
            startActivity(i);
        }else if(id==R.id.nav_rb){
            Intent i=new Intent(Request2.this,Request2.class);
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