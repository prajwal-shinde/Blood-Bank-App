package com.homeandlearn.ken.navigationframe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView mNavigationView;
    View mHeaderView;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private FirebaseAuth firebaseAuth;
    private TextView mName;
    private String name;
    private View navHeader;
    private TextView txtName;
    private Button recieve;
    private Button address;
    private Button Donate;
    private Button Request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recieve=(Button)findViewById(R.id.rec_button);
        address=(Button)findViewById(R.id.btn_address);
        Donate=(Button)findViewById(R.id.b1);
        Request=(Button)findViewById(R.id.b3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FirebaseAuth firebaseauth = FirebaseAuth.getInstance();
        NavigationView navigationView= ( NavigationView ) findViewById( R.id.nav_view );
        View headerview = navigationView.getHeaderView(0);
        TextView tt1 = (TextView) headerview.findViewById(R.id.user_1);
        tt1.setText(firebaseauth.getCurrentUser().getEmail());
        navigationView.setNavigationItemSelectedListener(this);
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Request2.class );
                startActivity(i);
            }
        });
        recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent i=new Intent(MainActivity.this,RequestActivity.class );
           startActivity(i);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Address.class );
                startActivity(i);
            }
        });
        Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Donate.class );
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setMessage("Are you sure want to exit?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.super.onBackPressed();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
           if(id==R.id.action_lfree )
           {
               logout();
               return true;
           }
           if(id==R.id.action_Help)
           {
               Intent i=new Intent(MainActivity.this,Help.class );
               startActivity(i);
           }
                return super.onOptionsItemSelected(item);
        }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i=new Intent(MainActivity.this,MainActivity.class );
            startActivity(i);
        } else if(id==R.id.nav_search)
        {
            Intent i=new Intent(MainActivity.this, MapsActivity.class);
            startActivity(i);
        }
         else if (id == R.id.nav_help) {
            Intent i=new Intent(MainActivity.this,Help.class );
            startActivity(i);
        } else if (id == R.id.nav_db) {
            Intent i=new Intent(MainActivity.this,Donate.class );
            startActivity(i);
        } else if (id == R.id.nav_Rb) {
            Intent i=new Intent(MainActivity.this,RequestActivity.class );
            startActivity(i);
        }else if(id==R.id.nav_about){
            Intent i=new Intent(MainActivity.this,About.class);
            startActivity(i);
        }else if(id==R.id.nav_rb){
            Intent i=new Intent(MainActivity.this,Request2.class);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(MainActivity.this,LoginActivity.class );
        startActivity(i);
        finish();
    }
}
