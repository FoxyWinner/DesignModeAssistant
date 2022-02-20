package com.designmodeassistant;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE)
//                        .setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // Perform anything for the action selected
//                            }
//                        })
//                        .show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //以下尝试将Fragment替换内容layout
        ContentFragment contentFragment =ContentFragment.newInstance(1,"ALL");
        //建立有1列的fragment
        FragmentManager fragmentManager =getFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,contentFragment);
        fragmentTransaction.commit();

    }
    /**
     * 屏幕旋转时调用此方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            //横向

        } else {
            //竖向
        }
    }
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_content)
        {
            FragmentManager fragmentManager =getFragmentManager();
            FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            ContentFragment contentFragment =ContentFragment.newInstance(1,"ALL");
            fragmentTransaction.replace(R.id.fragment_container,contentFragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_umlimages)
        {
            FragmentManager fragmentManager =getFragmentManager();
            FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            ContentFragment contentFragment =ContentFragment.newInstance(1,"UML");
            fragmentTransaction.replace(R.id.fragment_container,contentFragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_code)
        {
            FragmentManager fragmentManager =getFragmentManager();
            FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            ContentFragment contentFragment =ContentFragment.newInstance(1,"CODE");
            fragmentTransaction.replace(R.id.fragment_container,contentFragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_generalize)
        {
            //建立有1列的fragment
            FragmentManager fragmentManager =getFragmentManager();
            FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            ContentFragment contentFragment =ContentFragment.newInstance(1,"GENER");
            fragmentTransaction.replace(R.id.fragment_container,contentFragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_conact)
        {
            FragmentManager fragmentManager =getFragmentManager();
            FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            ContactUsFragment contactUsFragment =ContactUsFragment.newInstance();
            fragmentTransaction.replace(R.id.fragment_container,contactUsFragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
