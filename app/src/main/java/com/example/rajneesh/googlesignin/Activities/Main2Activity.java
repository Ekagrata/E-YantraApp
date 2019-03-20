package com.example.rajneesh.googlesignin.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rajneesh.googlesignin.NewsfeedFragment;
import com.example.rajneesh.googlesignin.R;
import com.example.rajneesh.googlesignin.newsfeedSearchResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    CircleImageView elsi;
//    EditText username, password;
//    TextView text;
//    String user,pass;
//    View view1;
//    Button posts;


    TextView comment, username;
    String comno;
    String strfragment;
    MaterialSearchView searchView;
    GoogleSignInClient googleSignInClient;
    EditText title;
    GoogleSignInAccount account;
    EditText desc;
    TextView upload;
    View view1, view2;
    String optionselected,nm,em,ph;


    EditText feedtext;
    SharedPreferences sharedPreferences1;

    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NewsFeed");
        inflater = this.getLayoutInflater();
     //   FragmentManager fragmentManager= getFragmentManager();

//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.container, newsfeedFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        searchView= findViewById(R.id.search_view);


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle= new Bundle();
                bundle.putString("searchstring",query);
                newsfeedSearchResult newsfeedsearchresult= new newsfeedSearchResult();
                newsfeedsearchresult.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,newsfeedsearchresult).addToBackStack(null)
                        .commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });





//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//
//            NotificationManager mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//            NotificationChannel mChannel=new NotificationChannel(Helper.CHANNEL_ID,Helper.CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
//
//            mChannel.setDescription(Helper.CHANNEL_DESCRPTION);
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            mChannel.shouldVibrate();
//            mChannel.setVibrationPattern(new long[]{100,200,300,400});
//
//            mNotificationManager.createNotificationChannel(mChannel);
//        }



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(Main2Activity.this,gso);


        display_form();


        username = findViewById(R.id.username);



        SharedPreferences sharedPreferences = getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("name", "");
//        username.setText(name);

        sharedPreferences1 = getSharedPreferences("choiceSelected", Context.MODE_PRIVATE);
        final String checking = sharedPreferences1.getString("optionSelected", "null");





//        elsi= findViewById(R.id.elsi);
//
//        elsi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Toast.makeText(Main2Activity.this,"clicked",Toast.LENGTH_SHORT).show();
//                LayoutInflater inflater = Main2Activity.this.getLayoutInflater();
//                view1= inflater.inflate(R.layout.alertdialoglayout,null);
//                text= view1.findViewById(R.id.text_header);
//                text.setText("eLSI");
//                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
//                // Get the layout inflater
//
//
//                // Inflate and set the layout for the dialog
//                // Pass null as the parent view because its going in the dialog layout
//
//                builder.setView(view1)
//
//                        // Add action buttons
//                        .setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                                // sign in the user ...
//
//                                username = view1.findViewById(R.id.username);
//                                password = view1.findViewById(R.id.password);
//
//                                 user = username.getText().toString();
//                                 pass = password.getText().toString();
//
//                                Toast.makeText(Main2Activity.this, user+" "+pass, Toast.LENGTH_SHORT).show();
//                                Call<Response> call = APIClient.getInstance().getApi().getResponse(user, pass);
//                                call.enqueue(new Callback<Response>() {
//                                    @Override
//                                    public void onResponse(Call<com.example.rajneesh.googlesignin.Response> call, retrofit2.Response<Response> response) {
//                                        com.example.rajneesh.googlesignin.Response response1 = response.body();
//                                        if (response1 != null) {
//                                            Toast.makeText(Main2Activity.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
////                                            if (response1.getMessage().equals("failed")) {
////                                                Toast.makeText(Main2Activity.this, "Please enter correct username and password", Toast.LENGTH_SHORT).show();
////                                            } else if (response1.getMessage().equals("success")) {
////                                                Toast.makeText(Main2Activity.this, "login successfull", Toast.LENGTH_SHORT).show();
////                                               // finish();
////                                                //Intent intent = new Intent(Main2Activity.this, landpage.class);
////                                                //startActivity(intent);
////
////                                            }
//                                        }
//
//
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Response> call, Throwable t) {
//                                        Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                      dialog.cancel();
//                            }
//                        });
//                 builder.create().show();
//            }
//        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        TextView nam = view.findViewById(R.id.navname);
        TextView email = view.findViewById(R.id.navemail);
        CircleImageView photo = view.findViewById(R.id.navphoto);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//       Log.d("bundle main",bundle.getString("name"));
         nm = bundle.getString("name");
         em = bundle.getString("email");
         ph = bundle.getString("photo");
        if (ph != null) {
            Log.d("photo", ph);
            Glide.with(this).load(ph).into(photo);
        }
        if (nm != null)
            nam.setText(nm);
        if (em != null)
            email.setText(em);

        // Picasso.get().load(ph).into(photo);

//        BackgroundTask backgroundTask= new BackgroundTask(this);
//        backgroundTask.execute("abc",nm,em);
        navigationView.setNavigationItemSelectedListener(this);


        //photo= findViewById(R.id.navphoto);
        // Name= findViewById(R.id.navname);
        // email= findViewById(R.id.navemail);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        MenuItem item= menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                searchView.setVisibility(View.VISIBLE);
//                return true;
//            }
//        });

        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.chatforum) {
            strfragment="Chat Forum";
            Intent intent = new Intent(this, PostsActivity.class);
            startActivity(intent);

        } else if (id == R.id.TalksandVideos) {
            Intent intent = new Intent(this, TalkVideosActivity.class);
            startActivity(intent);

        } else if (id == R.id.elsi) {
            Intent intent = new Intent(this, elsiActivity.class);
            startActivity(intent);

        } else if (id == R.id.eyrdc) {
            Intent intent = new Intent(this, Projects_eyrcActivity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            googleSignInClient.revokeAccess();
            googleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent it = new Intent(Main2Activity.this, MainActivity.class);
                            startActivity(it);
                            finish();
                        }
                    });
        }

        else if (id== R.id.profile){
            Intent intent= new Intent(this,EditProfileActivity.class);
            Bundle bundle= new Bundle();
            bundle.putString("name",nm);
            bundle.putString("email",em);
            bundle.putString("photo",ph);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        else if (id== R.id.calendar){
            Intent intent= new Intent(this,CalenderActivity.class);
            startActivity(intent);
        }

        else if(id==R.id.newsfeed){
            strfragment= "newsfeed";
            NewsfeedFragment newsfeedFragment= new NewsfeedFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,newsfeedFragment).addToBackStack(null)
                    .commit();
        }


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


//        private void fetchfeeds () {
//            retrofit2.Call<List<NewsFeedResponse>> call = APIClient.getInstance().getApi().getfeeds();
//            call.enqueue(new Callback<List<NewsFeedResponse>>() {
//                @Override
//                public void onResponse(retrofit2.Call<List<NewsFeedResponse>> call, retrofit2.Response<List<NewsFeedResponse>> response) {
//                    feeds = (ArrayList) response.body();
//                    adapter = new NewsfeedRecyclerAdapter(Main2Activity.this, feeds, new NewsfeedRecyclerAdapter.OnItemClickListner() {
//                        @Override
//                        public void OnItemClicked(int position) {
//                            // Toast.makeText(Main2Activity.this, "clicked", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void OnCommentSelected(int position) {
//                            Intent intent = new Intent(Main2Activity.this, CommentActivity.class);
//                            int id = feeds.get(position).getNewsid();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("id", id);
//                            bundle.putString("token", "newsfeed");
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                        }
//
//                        @Override
//                        public int getid(int position) {
//                            return id= feeds.get(position).getNewsid();
//
//
////                            Log.d("comno Main2",comno);
////                            return comno;
////
//                        }
//
//
//                    }, getWindowManager());
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//
//                }
//
//                @Override
//                public void onFailure(retrofit2.Call<List<NewsFeedResponse>> call, Throwable t) {
//                    Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }


        public void display_form () {
            view2 = inflater.inflate(R.layout.fieldcheckdialogbox_layout, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setView(view2);
            final Spinner dropdown = view2.findViewById(R.id.spinner1);
            final EditText college = view2.findViewById(R.id.college);
            final EditText jobtitle = view2.findViewById(R.id.jobtitle);
            final EditText company = view2.findViewById(R.id.company);
            final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Main2Activity.this, R.array.choices_array, R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);
            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    optionselected = dropdown.getSelectedItem().toString();
                    if (dropdown.getSelectedItem().toString().equals("Student")) {
                        college.setVisibility(View.VISIBLE);
                        jobtitle.setVisibility(View.GONE);
                        company.setVisibility(View.GONE);
                    } else if (dropdown.getSelectedItem().toString().equals("Professional")) {
                        jobtitle.setVisibility(View.VISIBLE);
                        company.setVisibility(View.VISIBLE);
                        college.setVisibility(View.GONE);
                    } else if (dropdown.getSelectedItem().toString().equals("Faculty")) {
                        college.setVisibility(View.VISIBLE);
                        jobtitle.setVisibility(View.GONE);
                        company.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //  Toast.makeText(Main2Activity.this, "ok selected", Toast.LENGTH_SHORT).show();
                    sharedPreferences1 = getSharedPreferences("choiceSelected", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("optionSelected", optionselected);
                    editor.apply();
                    String pref = sharedPreferences1.getString("optionSelected", "");
                    Log.d("putinshare", pref);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).show();
        }

    @Override
    protected void onStart() {
        super.onStart();
        NewsfeedFragment newsfeedFragment= new NewsfeedFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,newsfeedFragment).addToBackStack(null)
                .commit();
    }
}

