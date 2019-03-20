package com.example.rajneesh.googlesignin.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main3Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static int SPLASH_TIME = 4000;
    ImageButton signIn;
    GoogleApiClient googleApiClient;
    SharedPreferences sharedPreferences;
    GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        signIn = findViewById(R.id.googlebutton);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(Main3Activity.this,Main2Activity.class);
                startActivity(mySuperIntent);
                /* This 'finish()' is for exiting the app when back button pressed
                 *  from Home page which is ActivityHome
                 */
                finish();
            }
        }, SPLASH_TIME);

         sharedPreferences = getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        if (name != null && email != null) {
            Intent intent = new Intent(this, Main2Activity.class);
            // Intent intent= new Intent(this,GalleryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("email", email);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            signIn.setVisibility(View.VISIBLE);
        }


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, 1);


            }
        });
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do any action here. Now we are moving to next page
//                Intent mySuperIntent = new Intent(Main3Activity.this,Main2Activity.class);
//                startActivity(mySuperIntent);
//                /* This 'finish()' is for exiting the app when back button pressed
//                 *  from Home page which is ActivityHome
//                 */
//                finish();
//            }
//        }, SPLASH_TIME);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Check your Internet Connection", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                account = result.getSignInAccount();
                String name = account.getDisplayName();
                String email = account.getEmail();
                Call<ResponseBody> call = APIClient.getInstance().getApi().gettesting(name, email);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(Main3Activity.this, response.message() + "bla", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Main3Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                // BackgroundTask backgroundTask= new BackgroundTask(this);
                //backgroundTask.execute(method,email,name);


                SharedPreferences sharedPreferences = getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("email", email);
                editor.apply();

                Intent intent = new Intent(this, Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("email", email);
                intent.putExtras(bundle);
                startActivity(intent);




            }

        }
    }
}
