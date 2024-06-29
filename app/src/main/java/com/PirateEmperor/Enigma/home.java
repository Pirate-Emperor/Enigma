package com.PirateEmperor.Enigma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    static List<MenuModel> headerList = new ArrayList<>();
    static HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    static DrawerLayout drawer;
    // static List<MenuModel> headerList2 = new ArrayList<>();
    // static HashMap<MenuModel, List<MenuModel>> childList2 = new HashMap<>();
    static Context context;
    static TextView toolbartext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentActivity fa = this;

        toolbartext = findViewById(R.id.toolbartext);
        toolbartext.setText("Cryptography");

        // Window w = getWindow();
        // w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        // WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // // StatusBarUtil.setTransparent(this);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData(1);
        populateExpandableList(context, expandableListView, expandableListAdapter, 1, fa);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, homefragment.class, null)
                    // .addToBackStack("Stack")
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int num;
            if (toolbartext.getText().toString().equals("Cryptography")) {
                // toast("already");
                num = 0;
            } else {
                num = 1;
            }
            int count = getSupportFragmentManager().getBackStackEntryCount();
            // toast("count: "+count);
            for (int i = count; i > num; i--) {

                getSupportFragmentManager().popBackStack();

            }
            toolbartext.setText("Cryptography");

            super.onBackPressed();

        }
    }

    public static void prepareMenuData(int fr) {
        headerList.clear();
        childList.clear();
        // headerList2.clear();
        // childList2.clear();

        MenuModel menuModel;
        menuModel = new MenuModel("Home", true, false, new homefragment(), R.drawable.home); // Menu of Android
                                                                                             // Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Symmetric Algorithms", true, true, null, R.drawable.symmetric); // Menu of Java
                                                                                                   // Tutorials
        headerList.add(menuModel);
        // headerList2.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("AES Encryption", false, false, new symmetric(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("DES Encryption", false, false, new symmetric(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Blowfish Encryption", false, false, new symmetric(), 0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
            // childList2.put(menuModel, childModelsList);

        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Assymetric Algorithms", true, true, null, R.drawable.asymmetric); // Menu of Python
                                                                                                     // Tutorials
        headerList.add(menuModel);
        // headerList2.add(menuModel);
        childModel = new MenuModel("Diffle Hellman Cipher", false, false, new difflehellman(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("RSA Cipher", false, false, new rsa(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("DSA Cipher", false, false, new symmetric(), 0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
            // childList2.put(menuModel, childModelsList);

        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Hashing Algorithms", true, true, null, R.drawable.hashingm); // Menu of Python
                                                                                                // Tutorials
        headerList.add(menuModel);
        // headerList2.add(menuModel);

        childModel = new MenuModel("MD5", false, false, new hashing(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("SHA-1", false, false, new hashing(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("SHA-224", false, false, new hashing(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("SHA-256", false, false, new hashing(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("SHA-384", false, false, new hashing(), 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("SHA-512", false, false, new hashing(), 0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);

        }

    }

    public static void populateExpandableList(Context context1, ExpandableListView exv, ExpandableListAdapter exa,
            int fragment, FragmentActivity fragmentActivity) {

        exa = new ExpandableListAdapter(context1, headerList, childList, fragment);
        exv.setAdapter(exa);

        exv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        if (toolbartext.getText().toString().equals("Cryptography")) {
                            // toast("already");
                        } else {
                            fragmentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container_view, headerList.get(groupPosition).url)
                                    .setReorderingAllowed(true)
                                    .addToBackStack("Stack")
                                    .commit();
                            toolbartext.setText("Cryptography");

                        }

                        drawer.closeDrawer(GravityCompat.START);
                    }
                }

                return false;
            }
        });

        exv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                    long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.url != null) {
                        // toast("pos: "+model.url);

                        Fragment argumentFragment = model.url;// Get Fragment Instance
                        Bundle data = new Bundle();// Use bundle to pass data
                        data.putString("data", model.menuName);// put string, int, etc in bundle with a key value
                        argumentFragment.setArguments(data);// Finally set argument bundle to fragment

                        fragmentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_view, argumentFragment).setReorderingAllowed(true)
                                .addToBackStack("Stack").commit();

                        toolbartext.setText(model.menuName);

                        drawer.closeDrawer(GravityCompat.START);

                    }
                }

                return false;
            }
        });
    }

    static void toast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}